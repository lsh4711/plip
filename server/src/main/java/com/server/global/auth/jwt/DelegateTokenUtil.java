package com.server.global.auth.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.server.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DelegateTokenUtil { //TODO: JwtAuthenticationFilter에서 delegate~ 를 따로 분리한 이유->필터와 역할과 토큰 생성을 분리하기 위함?
	private final JwtTokenizer jwtTokenizer;

	public String delegateAccessToken(Member member) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", member.getEmail());
		claims.put("memberId", member.getMemberId());
		claims.put("roles", member.getRole().getRoles());
		String subject = member.getEmail();
		Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

		String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

		return accessToken;
	}

	public String delegateRefreshToken(Member member) {
		String subject = member.getEmail();
		Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

		String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

		return refreshToken;
	}
}
