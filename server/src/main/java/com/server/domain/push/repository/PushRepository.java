package com.server.domain.push.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.push.entity.Push;

public interface PushRepository extends JpaRepository<Push, Long> {
}
