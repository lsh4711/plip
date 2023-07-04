package com.server.global.auth.utils;

import org.springframework.stereotype.Component;

import com.server.domain.member.entity.Member;
import com.server.domain.member.repository.MemberRepository;
import com.server.domain.token.entity.RefreshToken;
import com.server.domain.token.service.RefreshTokenService;
import com.server.global.auth.jwt.DelegateTokenUtil;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AccessTokenRenewalUtil {
    private final MemberRepository memberRepository;
    private final RefreshTokenService refreshTokenService;
    private final DelegateTokenUtil delegateTokenUtil;

    public Token renewAccessToken(String accessToken) {
        RefreshToken findRefreshToken = refreshTokenService.getTokenByAccessToken(accessToken);
        String refreshToken = findRefreshToken.getRefreshToken();
        long memberId = Long.parseLong(findRefreshToken.getId());
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
        String newAccessToken = delegateTokenUtil.delegateAccessToken(member);

        refreshTokenService.saveTokenInfo(memberId, refreshToken, newAccessToken);
        return Token.builder()
            .accessToken(newAccessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
