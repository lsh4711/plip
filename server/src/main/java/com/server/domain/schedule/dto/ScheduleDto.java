package com.server.domain.schedule.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.server.domain.place.dto.PlaceDto;
import com.server.global.validator.DateValid;

import lombok.Getter;
import lombok.Setter;

public class ScheduleDto {
    @Getter
    @Setter
    public static class Post {
        private String title; // optional, default: "{city} 여행 레츠고!"
        private String content; // optional, default: "즐거운 여행~!"
        private Integer memberCount; // optional, default: 1

        @NotBlank
        private String region;

        @DateValid
        private LocalDate startDate;

        @DateValid
        private LocalDate endDate;

        @NotNull
        private List<PlaceDto.Post> placeDtos;
    }

    @Getter
    public static class Patch {
        private String title;
        private String content;
        private String city;
        private Integer memberCount;
        private LocalDate startDate;
        private LocalDate endDate;
        private List<PlaceDto.Post> places;
    }
}
