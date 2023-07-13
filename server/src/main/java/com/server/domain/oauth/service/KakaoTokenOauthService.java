package com.server.domain.oauth.service;

import org.springframework.stereotype.Service;

import com.server.domain.member.entity.Member;

import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.oauth.repository.KakaoTokenRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KakaoTokenOauthService {
    private final KakaoTokenRepository kakaoTokenRepository;

    public void saveToken(String accessToken, String refreshToken, Member member) {
        KakaoToken token = KakaoToken.builder()
            .AccessToken(accessToken)
            .RefreshToken(refreshToken)
            .member(member)
            .build();
        kakaoTokenRepository.save(token);
    }
}
