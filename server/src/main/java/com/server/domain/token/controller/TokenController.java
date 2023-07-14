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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/tokens")
@RestController
public class TokenController {
    private final JwtTokenizer jwtTokenizer;
    private final AccessTokenRenewalUtil accessTokenRenewalUtil;
    @GetMapping
    public ResponseEntity<?> getToken(HttpServletRequest request, HttpServletResponse response){
        Token token = accessTokenRenewalUtil.renewAccessToken(request);
        jwtTokenizer.setHeaderAccessToken(response, token.getAccessToken());
        jwtTokenizer.setHeaderRefreshToken(response, token.getRefreshToken());
        return ResponseEntity.ok().build();
    }
}
