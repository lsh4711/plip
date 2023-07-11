package com.server.domain.token.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.domain.token.entity.RefreshToken;
import com.server.domain.token.repository.RefreshTokenRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void saveTokenInfo(Long memberId, String refreshToken, String accessToken) {
        refreshTokenRepository.save(new RefreshToken(String.valueOf(memberId), refreshToken, accessToken));
    }

    public void removeRefreshToken(String accessToken) {
        refreshTokenRepository.findByAccessToken(accessToken)
            .ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));
    }

    @Transactional(readOnly = true)
    public RefreshToken getTokenByAccessToken(String accessToken) {
        return refreshTokenRepository.findByAccessToken(accessToken).orElseThrow(() -> new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));
    }

}
