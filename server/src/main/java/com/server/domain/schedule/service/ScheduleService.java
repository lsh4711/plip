package com.server.domain.schedule.service;

import org.springframework.stereotype.Service;

import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.repository.ScheduleRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.CustomBeanUtils;
import com.server.global.utils.CustomUtil;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule saveSchedule(Schedule schedule) {
        String region = schedule.getRegion();
        String title = schedule.getTitle();
        String content = schedule.getContent();

        if (title == null) {
            schedule.setTitle(String.format("%s 여행 레츠고!", region));
        }
        if (content == null) {
            schedule.setContent(String.format("즐거운 %s 여행~!", region));
        }

        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Schedule schedule) {
        long scheduleId = schedule.getScheduleId();
        Schedule foundSchedule = findSchedule(scheduleId);

        CustomBeanUtils.copyNonNullProperties(schedule, foundSchedule);
        saveSchedule(foundSchedule);

        return foundSchedule;
    }

    public Schedule findSchedule(long scheduleId) {
        long memberId = CustomUtil.getAuthId();
        Schedule schedule = scheduleRepository
                .findByScheduleIdAndMember_MemberId(scheduleId, memberId);

        if (schedule == null) {
            throw new CustomException(
                ExceptionCode.SCHEDULE_NOT_FOUND);
        }

        return schedule;
    }

    public void deleteSchedule(long scheduleId) {
        long memberId = CustomUtil.getAuthId();

        verify(scheduleId, memberId);
        scheduleRepository.deleteById(scheduleId);
    }

    public void verify(long scheduleId, long memberId) {
        boolean exists = scheduleRepository
                .existsByScheduleIdAndMember_MemberId(scheduleId, memberId);

        if (!exists) {
            throw new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND);
        }

    }
}
