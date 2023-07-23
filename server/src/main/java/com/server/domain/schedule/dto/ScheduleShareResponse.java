package com.server.domain.schedule.dto;

import java.util.List;
import java.util.Map;

import com.server.domain.record.dto.RecordDto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ScheduleShareResponse {
    private ScheduleResponse schedule;
    private Map<Long, List<RecordDto.Response>> recordsMap;
}
