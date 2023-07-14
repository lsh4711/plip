package com.server.domain.schedule.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.mapstruct.Mapper;

import com.server.domain.member.entity.Member;
import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.dto.ScheduleResponse;
import com.server.domain.schedule.entity.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    default Schedule postDtoToSchedule(ScheduleDto.Post postDto) {
        if (postDto == null) {
            return null;
        }

        Schedule schedule = new Schedule();
        LocalDate startDate = postDto.getStartDate();
        LocalDate endDate = postDto.getEndDate();
        int period = endDate.compareTo(startDate);

        schedule.setContent(postDto.getContent());
        schedule.setEndDate(endDate);
        schedule.setMemberCount(postDto.getMemberCount());
        schedule.setRegion(postDto.getRegion());
        schedule.setStartDate(startDate);
        schedule.setTitle(postDto.getTitle());
        schedule.setPeriod(period + 1);

        return schedule;
    }

    default Schedule patchDtoToSchedule(ScheduleDto.Patch patchDto) {
        if (patchDto == null) {
            return null;
        }

        Schedule schedule = new Schedule();
        LocalDate startDate = patchDto.getStartDate();
        LocalDate endDate = patchDto.getEndDate();

        if (startDate != null && endDate != null) {
            int period = endDate.compareTo(startDate);
            schedule.setPeriod(period + 1);
        }
        schedule.setContent(patchDto.getContent());
        schedule.setEndDate(endDate);
        schedule.setMemberCount(patchDto.getMemberCount());
        schedule.setRegion(patchDto.getRegion());
        schedule.setStartDate(startDate);
        schedule.setTitle(patchDto.getTitle());

        return schedule;
    }

    // @Mapping(source = "member.memberId", target = "memberId")
    // @Mapping(source = "member.nickname", target = "nickname")
    default ScheduleResponse scheduleToScheduleResponse(Schedule schedule) {
        if (schedule == null) {
            return null;
        }

        Member member = schedule.getMember();
        LocalDateTime startDate = schedule.getStartDate().atStartOfDay();
        LocalDateTime endDate = schedule.getEndDate().atStartOfDay();
        ScheduleResponse scheduleResponse = new ScheduleResponse();

        scheduleResponse.setMemberId(member.getMemberId());
        scheduleResponse.setNickname(member.getNickname());
        scheduleResponse.setStartDate(startDate);
        scheduleResponse.setEndDate(endDate);
        scheduleResponse.setContent(schedule.getContent());
        scheduleResponse.setCreatedAt(schedule.getCreatedAt());
        scheduleResponse.setMemberCount(schedule.getMemberCount());
        scheduleResponse.setModifiedAt(schedule.getModifiedAt());
        scheduleResponse.setPeriod(schedule.getPeriod());
        scheduleResponse.setRegion(schedule.getRegion());
        scheduleResponse.setScheduleId(schedule.getScheduleId());
        scheduleResponse.setTitle(schedule.getTitle());

        return scheduleResponse;
    }
}
