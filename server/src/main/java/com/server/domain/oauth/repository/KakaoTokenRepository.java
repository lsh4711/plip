package com.server.domain.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.oauth.entity.KakaoToken;

public interface KakaoTokenRepository extends JpaRepository<KakaoToken, Long> {
    KakaoToken findByMember_MemberId(long memberId);
}
