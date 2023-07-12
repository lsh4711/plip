package com.server.domain.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.test.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
    Test findByMember_MemberId(long memberId);
}
