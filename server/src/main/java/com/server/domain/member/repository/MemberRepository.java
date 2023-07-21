package com.server.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.server.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    @Query("select m from Member m left join fetch m.kakaoToken where m.email = :email") //양방향 OneToOne N+1문제
    //@EntityGraph(attributePaths = {"kakaoToken"})
    Optional<Member> findByEmail(String email);
}
