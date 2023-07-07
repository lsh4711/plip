package com.server.domain.schedule.mapper;

import org.mapstruct.Mapper;

import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.dto.ScheduleResponse;
import com.server.domain.schedule.entity.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule postDtoToSchedule(ScheduleDto.Post postDto);

    ScheduleResponse scheduleToScheduleResponse(Schedule schedule);
}
