package com.server.domain.schedule.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.mapper.ScheduleMapper;
import com.server.domain.schedule.service.ScheduleService;
import com.server.global.utils.UriCreator;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private ScheduleService scheduleService;
    private ScheduleMapper scheduleMapper;

    public ScheduleController(ScheduleService scheduleService, ScheduleMapper scheduleMapper) {
        this.scheduleService = scheduleService;
        this.scheduleMapper = scheduleMapper;
    }

    @PostMapping
    public ResponseEntity postSchedule(ScheduleDto.Post postDto) {
        Schedule schedule = scheduleMapper.postDtoToSchedule(postDto); // 여행지 분리해야함
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        URI location = UriCreator.createUri("schedules", savedSchedule.getScheduleId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity getPlaces() {
        return null;
    }
}
