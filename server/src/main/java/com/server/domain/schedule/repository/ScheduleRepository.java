package com.server.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByScheduleIdAndMember_MemberId(long scheduleId,
            long memberId);

    Schedule findByScheduleIdAndMember_MemberId(long scheduleId,
            long memberId);

    Schedule findByScheduleIdAndMember_MemberIdAndMember_Email(long scheduleId,
            long memberId, String email);
}
