package com.server.domain.schedule.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.dto.ScheduleResponse;
import com.server.domain.schedule.entity.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule postDtoToSchedule(ScheduleDto.Post postDto);

    Schedule patchDtoToSchedule(ScheduleDto.Patch patchDto);

    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "member.nickname", target = "nickname")
    ScheduleResponse scheduleToScheduleResponse(Schedule schedule);
}
