package com.server.domain.mail.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@RedisHash(value = "authMailCode", timeToLive = 60 * 10)
public class AuthMailCode {
    @Id
    private String id;
    private String authCode;
    @Indexed
    private String email;
}
