package com.server.global.auth.handler.logout;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.server.domain.member.entity.Member;
import com.server.domain.token.entity.RefreshToken;
import com.server.domain.token.service.RedisUtils;
import com.server.domain.token.service.RefreshTokenService;
import com.server.global.auth.jwt.JwtTokenizer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberLogoutHandler implements LogoutHandler {
    private final RedisUtils redisUtils;
    private final JwtTokenizer jwtTokenizer;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accessToken = jwtTokenizer.getHeaderAccessToken(request);
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Date expiration = jwtTokenizer.getClaims(accessToken, base64EncodedSecretKey).getBody().getExpiration();
        Long now = new Date().getTime();
        redisUtils.setBlackList(accessToken, "accessToken",expiration.getTime() - now);
    }
}
