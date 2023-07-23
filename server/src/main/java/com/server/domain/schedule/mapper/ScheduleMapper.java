package com.server.domain.schedule.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.mapstruct.Mapper;

import com.server.domain.member.entity.Member;
import com.server.domain.region.entity.Region;
import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.dto.ScheduleResponse;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    default Schedule postDtoToSchedule(ScheduleDto.Post postDto) {
        if (postDto == null) {
            return null;
        }

        String engName = postDto.getRegion();
        Region region = new Region();
        region.setEngName(engName);

        Schedule schedule = new Schedule();
        LocalDate startDate = postDto.getStartDate();
        LocalDate endDate = postDto.getEndDate();
        int period = (int)ChronoUnit.DAYS.between(startDate, endDate);

        schedule.setEndDate(endDate);
        schedule.setMemberCount(postDto.getMemberCount());
        schedule.setRegion(region);
        schedule.setStartDate(startDate);
        schedule.setTitle(postDto.getTitle());
        schedule.setPeriod(period + 1);

        return schedule;
    }

    default Schedule patchDtoToSchedule(ScheduleDto.Patch patchDto) {
        if (patchDto == null) {
            return null;
        }

        String engName = patchDto.getRegion();
        Region region = new Region();
        region.setEngName(engName);

        Schedule schedule = new Schedule();
        LocalDate startDate = patchDto.getStartDate();
        LocalDate endDate = patchDto.getEndDate();

        if (startDate != null && endDate != null) {
            int period = (int)ChronoUnit.DAYS.between(startDate, endDate);
            schedule.setPeriod(period + 1);
        }
        schedule.setEndDate(endDate);
        schedule.setMemberCount(patchDto.getMemberCount());
        schedule.setRegion(region);
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
        Region region = schedule.getRegion();
        String engName = region.getEngName();
        String korName = region.getKorName();

        Member member = schedule.getMember();
        LocalDateTime startDate = schedule.getStartDate().atStartOfDay();
        LocalDateTime endDate = schedule.getEndDate().atStartOfDay();

        List<SchedulePlace> schedulePlaces = schedule.getSchedulePlaces();
        int placeSize = schedulePlaces.size();

        ScheduleResponse scheduleResponse = new ScheduleResponse();

        if (member != null) {
            scheduleResponse.setMemberId(member.getMemberId());
            scheduleResponse.setNickname(member.getNickname());
        }
        scheduleResponse.setStartDate(startDate);
        scheduleResponse.setEndDate(endDate);
        scheduleResponse.setCreatedAt(schedule.getCreatedAt());
        scheduleResponse.setMemberCount(schedule.getMemberCount());
        scheduleResponse.setModifiedAt(schedule.getModifiedAt());
        scheduleResponse.setPeriod(schedule.getPeriod());
        scheduleResponse.setRegion(engName);
        scheduleResponse.setKorRegion(korName);
        scheduleResponse.setScheduleId(schedule.getScheduleId());
        scheduleResponse.setTitle(schedule.getTitle());
        scheduleResponse.setPlaceSize(placeSize);

        return scheduleResponse;
    }
}
