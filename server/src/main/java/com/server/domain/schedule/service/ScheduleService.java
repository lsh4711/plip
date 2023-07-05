package com.server.domain.schedule.service;

import org.springframework.stereotype.Service;

import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.repository.ScheduleRepository;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

}
