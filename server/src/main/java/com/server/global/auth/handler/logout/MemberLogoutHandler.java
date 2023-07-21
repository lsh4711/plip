package com.server.global.auth.handler.logout;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.server.domain.token.service.RedisUtils;

import com.server.global.auth.jwt.JwtTokenizer;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MemberLogoutHandler implements LogoutHandler {
    private final RedisUtils redisUtils;
    private final JwtTokenizer jwtTokenizer;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try{
            String accessToken = jwtTokenizer.getHeaderAccessToken(request);
            String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
            Date expiration = jwtTokenizer.getClaims(accessToken, base64EncodedSecretKey).getBody().getExpiration();
            Long now = new Date().getTime();
            //TODO : 유효 시간
            redisUtils.setBlackList(accessToken, "accessToken",expiration.getTime() - now);
        }catch (ExpiredJwtException eje){
            log.error("### 토큰이 만료되었습니다. 그대로 로그아웃합니다.");
        }

    }
}
