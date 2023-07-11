package com.server.domain.record.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.record.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Page<Record> findByMemberMemberId(Pageable pageable, Long memberId);

    boolean existsByRecordIdAndMember_MemberId(long recordId, long memberId);
}
