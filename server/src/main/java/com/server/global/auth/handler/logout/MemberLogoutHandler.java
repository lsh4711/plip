package com.server.global.auth.handler.logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.server.domain.token.service.RefreshTokenService;
import com.server.global.auth.jwt.JwtTokenizer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberLogoutHandler implements LogoutHandler {
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenizer jwtTokenizer;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        refreshTokenService.removeRefreshToken(jwtTokenizer.getHeaderAccessToken(request));
    }
}
