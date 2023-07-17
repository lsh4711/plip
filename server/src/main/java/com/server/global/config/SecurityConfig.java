package com.server.global.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.server.domain.member.repository.MemberRepository;
import com.server.domain.oauth.service.KakaoTokenOauthService;
import com.server.domain.token.service.RedisUtils;
import com.server.global.auth.handler.login.MemberAuthenticationEntryPoint;
import com.server.global.auth.handler.logout.MemberLogoutHandler;
import com.server.global.auth.handler.logout.MemberLogoutSuccessHandler;
import com.server.global.auth.handler.oauth.OAuth2SuccessHandler;
import com.server.global.auth.jwt.DelegateTokenUtil;
import com.server.global.auth.jwt.JwtTokenizer;
import com.server.global.auth.userdetails.CustomOAuth2UserService;
import com.server.global.auth.utils.AccessTokenRenewalUtil;
import com.server.global.auth.utils.OAuth2TokenUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final AccessTokenRenewalUtil accessTokenRenewalUtil;
    private final DelegateTokenUtil delegateTokenUtil;
    private final CustomOAuth2UserService oAuth2UserService;
    private final MemberRepository memberRepository;
    private final RedisUtils redisUtils;
    private final OAuth2TokenUtils oAuth2TokenUtils;
    private final KakaoTokenOauthService kakaoTokenOauthService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .logoutUrl("/api/users/logout")
                .deleteCookies("Refresh")
                .addLogoutHandler(new MemberLogoutHandler(redisUtils, jwtTokenizer))
                .logoutSuccessHandler(new MemberLogoutSuccessHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .and()
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint()
                        .userService(oAuth2UserService)
                        .and()
                        .successHandler(
                            new OAuth2SuccessHandler(delegateTokenUtil, memberRepository, jwtTokenizer,
                                oAuth2TokenUtils, kakaoTokenOauthService)))
                .apply(customFilterConfigurers())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.GET, "/*/places/*/records").permitAll()
                        .antMatchers(HttpMethod.GET, "/*/records/*").permitAll()
                        .antMatchers(HttpMethod.GET, "/*/schedules/*/share").permitAll()
                        .antMatchers("/*/users").authenticated()
                        .antMatchers("/*/records").authenticated()
                        .antMatchers("/*/records/**").authenticated()
                        .antMatchers("/*/places/**").authenticated()
                        .antMatchers("/*/schedules/**").authenticated()
                        .anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public CustomFilterConfig customFilterConfigurers() {
        return new CustomFilterConfig(jwtTokenizer, delegateTokenUtil, accessTokenRenewalUtil, redisUtils);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(
            List.of("http://127.0.0.1:5173",
                "http://localhost:5173",
                "http://localhost:8000",
                "https://plip.netlify.app"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Refresh");
        configuration.addExposedHeader("Location");
        configuration.addExposedHeader("Set-Cookie");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
