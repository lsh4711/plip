package com.server.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import com.server.domain.member.repository.MemberRepository;
import com.server.domain.token.service.RefreshTokenService;
import com.server.global.auth.filter.JwtAuthenticationFilter;
import com.server.global.auth.filter.JwtVerificationFilter;
import com.server.global.auth.handler.MemberAuthenticationFailureHandler;
import com.server.global.auth.handler.MemberAuthenticationSuccessHandler;
import com.server.global.auth.jwt.DelegateTokenUtil;
import com.server.global.auth.jwt.JwtTokenizer;
import com.server.global.auth.utils.AccessTokenRenewalUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class CustomFilterConfig extends AbstractHttpConfigurer<CustomFilterConfig, HttpSecurity> {
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;
    private final DelegateTokenUtil delegateTokenUtil;
    private final AccessTokenRenewalUtil accessTokenRenewalUtil;

    @Override
    public void configure(HttpSecurity builder) {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager,
            refreshTokenService, delegateTokenUtil);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/users/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

        JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, accessTokenRenewalUtil);

        builder
            .addFilter(jwtAuthenticationFilter)
            .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
    }
}
