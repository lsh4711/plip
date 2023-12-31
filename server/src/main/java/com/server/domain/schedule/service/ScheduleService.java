package com.server.domain.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.server.domain.member.entity.Member;
import com.server.domain.push.service.PushService;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.repository.ScheduleRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.AuthUtil;
import com.server.global.utils.CustomBeanUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    private final PushService pushService;

    private final PasswordEncoder passwordEncoder;

    @Value("${share.key}")
    private String shareSecretKey;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Schedule schedule) {
        long scheduleId = schedule.getScheduleId();
        Schedule foundSchedule = findScheduleOfAuthMember(scheduleId);

        CustomBeanUtils.copyNonNullProperties(schedule, foundSchedule);

        return scheduleRepository.save(foundSchedule);
    }

    public Schedule findScheduleOfAuthMember(long scheduleId) {
        long memberId = AuthUtil.getMemberId();
        Schedule schedule = scheduleRepository
                .findByScheduleIdAndMember_MemberId(scheduleId, memberId);

        if (schedule == null) {
            throw new CustomException(
                ExceptionCode.SCHEDULE_NOT_FOUND);
        }

        return schedule;
    }

    public List<Schedule> findSchedulesOfAuthMember() {
        Sort sort = Sort.by("createdAt").descending();
        long memberId = AuthUtil.getMemberId();
        List<Schedule> schedules = scheduleRepository.findAllByMember_memberId(memberId, sort);

        return schedules;
    }

    public Schedule findSharedSchedule(long scheduleId, long memberId, String code) {
        Schedule schedule = scheduleRepository
                .findByScheduleIdAndMember_MemberId(scheduleId, memberId);

        if (schedule == null) {
            throw new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND);
        }

        Member member = schedule.getMember();
        String email = member.getEmail();
        String raw = String.format("%d/%s/%s",
            memberId,
            email,
            shareSecretKey);
        String encoded = "{bcrypt}$2a$10$" + code;

        if (!passwordEncoder.matches(raw, encoded)) {
            throw new CustomException(ExceptionCode.SHARE_CODE_INVALID);
        }

        return schedule;
    }

    public String createShareUrl(long scheduleId, Member member) {
        long memberId = member.getMemberId();
        String email = member.getEmail();

        verify(scheduleId, memberId);

        String raw = String.format("%d/%s/%s",
            memberId,
            email,
            shareSecretKey);
        String code = passwordEncoder.encode(raw)
                .replace("{bcrypt}$2a$10$", "");

        String shareUrl = String.format("https://plip.netlify.app/plan/detail/%d/share?id=%d&code=%s",
            scheduleId,
            memberId,
            code);

        return shareUrl;
    }

    public Schedule deleteSchedule(long scheduleId) {
        Schedule schedule = findScheduleOfAuthMember(scheduleId);

        scheduleRepository.deleteById(scheduleId);

        return schedule;
    }

    public void verify(long scheduleId, long memberId) {
        boolean exists = scheduleRepository
                .existsByScheduleIdAndMember_MemberId(scheduleId, memberId);

        if (!exists) {
            throw new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND);
        }

    }
}
