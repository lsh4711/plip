package com.server.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
