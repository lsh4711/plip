package com.server.domain.schedule.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.server.domain.place.dto.PlaceDto;
import com.server.global.validator.DateValid;

import lombok.Getter;

public class ScheduleDto {
    @Getter
    public static class Post {
        // private String title; // optional, default: "{korRegion} 여행 레츠고!"
        // private int memberCount; // optional, default: 1

        @NotBlank
        private String engRegion;

        @DateValid
        private LocalDate startDate;

        @DateValid
        private LocalDate endDate;
    }

    @Getter
    public static class Patch { // 보내준 일정을 수정해서 요청하는 경우를 가정
        @NotBlank
        private String title;

        @NotNull
        private Integer memberCount;

        @NotBlank
        private String engRegion;

        @DateValid
        private LocalDate startDate;

        @DateValid
        private LocalDate endDate;

        private List<List<PlaceDto.@Valid Patch>> places; // 마이페이지에서 수정하는 경우는 값이 없을 수도 있음
    }
}
