package com.server.domain.schedule.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.schedule.entity.SchedulePlace;

public interface SchedulePlaceRepository extends JpaRepository<SchedulePlace, Long> {
    Optional<SchedulePlace> findByPlacePlaceId(Long placeId);

}
