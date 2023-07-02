package com.server.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
