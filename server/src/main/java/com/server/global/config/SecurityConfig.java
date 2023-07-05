package com.server.global.config;

import static org.springframework.security.config.Customizer.*;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.server.domain.token.service.RefreshTokenService;
import com.server.global.auth.handler.MemberAuthenticationEntryPoint;
import com.server.global.auth.jwt.DelegateTokenUtil;
import com.server.global.auth.jwt.JwtTokenizer;
import com.server.global.auth.utils.AccessTokenRenewalUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenRenewalUtil accessTokenRenewalUtil;
    private final DelegateTokenUtil delegateTokenUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .headers().frameOptions().sameOrigin()
            .and()
            .csrf().disable()
            .cors(withDefaults())
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
            .and()
            .apply(customFilterConfigurers())
            .and()
            .authorizeHttpRequests(authorize -> authorize
                .antMatchers("/*/users/**").permitAll()
                .anyRequest().permitAll()
            );

        return http.build();
    }

    @Bean
    public CustomFilterConfig customFilterConfigurers() {
        return new CustomFilterConfig(jwtTokenizer, refreshTokenService, delegateTokenUtil, accessTokenRenewalUtil);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
