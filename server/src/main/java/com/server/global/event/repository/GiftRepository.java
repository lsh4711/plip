package com.server.global.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.global.event.entity.Gift;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    Gift findByEmail(String email);
}
