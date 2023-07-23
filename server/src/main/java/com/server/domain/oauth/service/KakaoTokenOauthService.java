package com.server.domain.oauth.service;

import java.util.Optional;

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
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .member(member)
            .build();
        Optional<KakaoToken> findToken = kakaoTokenRepository.findByMember_MemberId(member.getMemberId());
        if (findToken.isEmpty())
            kakaoTokenRepository.save(token);
        else
            findToken.get().setAccessToken(accessToken);
    }

    public void saveTestToken(KakaoToken kakaoToken) {
        kakaoTokenRepository.save(kakaoToken);
    }
}