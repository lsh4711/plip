package com.server.domain.schedule.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.server.domain.place.dto.PlaceResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleResponse {
    private Long memberId;
    private String nickname;

    private Long scheduleId;
    private String region;
    private String title;
    private String content;
    private Integer memberCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<PlaceResponse> places;
}
