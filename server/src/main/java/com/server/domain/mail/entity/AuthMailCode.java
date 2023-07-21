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
/*
     Redis 해시 키의 이름을 authMailCode로 정하고,
     edis 해시 키의 TTL(Time-To-Live)을 10분 동안으로 정한다.
     인증 코드는 일회성 정보이므로 이렇게 만료 시간이 지나면 자동 삭제되도록 구현
 */
public class AuthMailCode {
    @Id
    private String id;
    private String authCode;
    @Indexed
    private String email;
}
