package com.server.global.auth.utils;

import java.util.Map;

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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AccessTokenRenewalUtil {
    private final MemberRepository memberRepository;
    private final DelegateTokenUtil delegateTokenUtil;
    private final JwtTokenizer jwtTokenizer;

    // TODO: 쿠키 재발급으로 변경, 레디스로 검증을 한번 할지 물어보고 리팩토링
    public Token renewAccessToken(HttpServletRequest request) {
        try{
            String refreshToken = jwtTokenizer.getHeaderRefreshToken(request);
            String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
            String email = jwtTokenizer.getClaims(refreshToken, base64EncodedSecretKey).getBody().getSubject();
            Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
            String newAccessToken = delegateTokenUtil.delegateAccessToken(member);
            return Token.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
        }catch (CustomException ce){
            throw ce;
        }
    }

}
