package com.server.domain.schedule.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.repository.ScheduleRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule findSchedule(long scheduleId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);

        return optionalSchedule.orElseThrow(() -> new CustomException(
            ExceptionCode.SCHEDULE_NOT_FOUND));
    }

    public void deleteSchedule(long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    public void verfify(long memberId, long scheduleId) {
        boolean exists = scheduleRepository
                .existsByScheduleIdAndMember_MemberId(scheduleId, memberId);
        if (!exists) {
            throw new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND);
        }
    }
}
