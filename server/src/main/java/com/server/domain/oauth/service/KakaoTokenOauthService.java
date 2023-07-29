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

    public void saveOrUpdateToken(String accessToken, String refreshToken, Member member) {
        Optional<KakaoToken> findToken = kakaoTokenRepository.findByMember(member);
        if (findToken.isEmpty()) {
            KakaoToken token = KakaoToken.builder()
                .member(member)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
            kakaoTokenRepository.save(token);
        } else {
            findToken.get().updateToken(accessToken, refreshToken);
        }
    }

    public void saveTestToken(KakaoToken kakaoToken) {
        kakaoTokenRepository.save(kakaoToken);
    }
}
