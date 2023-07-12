package com.server.domain.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.test.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
    Test findByTaskId(long taskId);

    List<Test> findAllOrderByTaskId();
}
