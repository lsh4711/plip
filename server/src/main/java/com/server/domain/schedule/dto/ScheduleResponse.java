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
    private Integer memberCount;

    // 기존 코드
    // private LocalDate startDate;
    // private LocalDate endDate;

    // 클라이언트 요청사항
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer period;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Integer placeSize;
    private List<List<PlaceResponse>> places;
}
