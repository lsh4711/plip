package com.server.domain.schedule.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.server.domain.place.dto.PlaceDto;

import lombok.Getter;

public class ScheduleDto {
    @Getter
    public static class Post {
        private String title; // optional, default: "{city} 여행 레츠고!"
        private String content; // optional, default: "즐거운 여행~!"
        private Integer memberCount; // optional, default: 1

        @NotBlank
        private String city;

        @NotBlank
        private LocalDate startDate;

        @NotBlank
        private LocalDate endDate;

        @NotBlank
        private List<PlaceDto.Post> places;
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
