package com.server.domain.test.service;

import org.springframework.stereotype.Service;

import com.server.domain.test.auth.KakaoAuth;
import com.server.domain.test.auth.Token;

@Service
public class KakaoService {
    private KakaoAuth kakaoAuth;

    public KakaoService(KakaoAuth kakaoAuth) {
        this.kakaoAuth = kakaoAuth;
    }

    public String sendMessage(String accessToken, String message) {
        try {
            kakaoAuth.sendMessage(accessToken, message);
        } catch (Exception e) {
            return "메시지 전송 실패. 오류 제보 부탁드립니다.";
        }

        return "메시지 전송 성공: " + message;
    }

    public Token getTokens(String code, Token tokens) {
        tokens = kakaoAuth.requestTokens(code);
        // tokens = kakaoAuth.refreshTokens();

        return tokens;
    }

    public boolean isValid(Token tokens) {
        return true;
    }
}
