package com.server.domain.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.record.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {

}