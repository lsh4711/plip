package com.server.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.schedule.entity.SchedulePlace;

public interface SchedulePlaceRepository extends JpaRepository<SchedulePlace, Long> {

}
