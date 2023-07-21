package com.server.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    // 테스트 해야함, 지연 로딩으로도 해결 가능한지 확인필요
    // @Query("select m from Member m left join fetch m.kakaoToken where m.email = :email") // kakaoToken이 없음
    Optional<Member> findByEmail(String email);
}
