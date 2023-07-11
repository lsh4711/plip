package com.server.global.auth.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.server.domain.member.entity.Member;
import com.server.domain.member.repository.MemberRepository;
import com.server.domain.token.entity.RefreshToken;
import com.server.domain.token.service.RefreshTokenService;
import com.server.global.auth.jwt.DelegateTokenUtil;
import com.server.global.auth.jwt.JwtTokenizer;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AccessTokenRenewalUtil {
    private final MemberRepository memberRepository;
    private final RefreshTokenService refreshTokenService;
    private final DelegateTokenUtil delegateTokenUtil;
    private final JwtTokenizer jwtTokenizer;

    // TODO: 재발급을 쿠키에 있는 걸로 쓸지, 레디스로 검증을 한번 할지 물어보고 리팩토링
    public Token renewAccessToken(HttpServletRequest request) {
        try{
            String accessToken = jwtTokenizer.getHeaderAccessToken(request);
            String userRefreshToken = jwtTokenizer.getHeaderRefreshToken(request);

            RefreshToken findRefreshToken = refreshTokenService.getTokenByAccessToken(accessToken);
            String redisRefreshToken = findRefreshToken.getRefreshToken();
            long memberId = Long.parseLong(findRefreshToken.getId());
            Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
            String newAccessToken = delegateTokenUtil.delegateAccessToken(member);

            refreshTokenService.saveTokenInfo(memberId, redisRefreshToken, newAccessToken);
            return Token.builder()
                .accessToken(newAccessToken)
                .refreshToken(redisRefreshToken)
                .build();
        }catch (CustomException ce){
            throw ce;
        }
    }
}
