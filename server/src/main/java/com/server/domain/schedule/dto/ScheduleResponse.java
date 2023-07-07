package com.server.domain.schedule.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.server.domain.member.entity.Member;
import com.server.domain.place.dto.PlaceResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleResponse {
    private Long scheduleId;
    private String region;
    private String title;
    private String content;
    private Integer memberCount;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Member member;

    private List<PlaceResponse> places;
}
