package com.server.domain.oauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.member.entity.Member;
import com.server.domain.oauth.entity.KakaoToken;

public interface KakaoTokenRepository extends JpaRepository<KakaoToken, Long> {
    Optional<KakaoToken> findByAccessToken(String accessToken);

    Optional<KakaoToken> findByMember(Member member);
}
