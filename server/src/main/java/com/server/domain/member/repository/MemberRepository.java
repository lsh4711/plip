package com.server.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
<<<<<<< HEAD
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<Member> findByEmail(String email);
=======
    Optional<Member> findByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
}
