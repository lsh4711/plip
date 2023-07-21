package com.server.domain.token.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 3)
public class RefreshToken {
	@Id
	private String id;
	private String refreshToken;
	@Indexed
	private String accessToken;
}
