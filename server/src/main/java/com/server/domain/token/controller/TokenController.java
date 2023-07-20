package com.server.domain.token.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.global.auth.jwt.JwtTokenizer;
import com.server.global.auth.utils.AccessTokenRenewalUtil;
import com.server.global.auth.utils.Token;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/tokens")
@RestController
public class TokenController {
    private final JwtTokenizer jwtTokenizer;
    private final AccessTokenRenewalUtil accessTokenRenewalUtil;

    @GetMapping
    public ResponseEntity<?> getToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            Token token = accessTokenRenewalUtil.renewAccessToken(request);
            jwtTokenizer.setHeaderAccessToken(response, token.getAccessToken());
            jwtTokenizer.setHeaderRefreshToken(response, token.getRefreshToken());
            return ResponseEntity.ok().build();
        } catch (ExpiredJwtException je) {
            log.error("### 리프레쉬 토큰을 찾을 수 없음");
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        }
    }
}
