package com.server.domain.oauth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.domain.member.entity.Member;
import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.oauth.repository.KakaoTokenRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class KakaoTokenOauthService {
    private final KakaoTokenRepository kakaoTokenRepository;

    public void saveToken(String accessToken, String refreshToken, Member member) {
        KakaoToken token = KakaoToken.builder()
                .member(member)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        KakaoToken findToken = member.getKakaoToken();
        if (findToken == null)
            kakaoTokenRepository.save(token);
        else
            findToken.setAccessToken(accessToken);
    }

    public void saveTestToken(KakaoToken kakaoToken) {
        kakaoTokenRepository.save(kakaoToken);
    }
}
