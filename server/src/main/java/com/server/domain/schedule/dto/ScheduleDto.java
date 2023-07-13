package com.server.domain.schedule.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.server.domain.place.dto.PlaceDto;
import com.server.global.validator.DateValid;
import com.server.global.validator.OptionalDateValid;

import lombok.Getter;
import lombok.Setter;

public class ScheduleDto {
    @Getter
    @Setter
    public static class Post {
        private String title; // optional, default: "{region} 여행 레츠고!"
        private String content; // optional, default: "즐거운 {region} 여행~!"
        private int memberCount = 1; // optional, default: 1

        @NotBlank
        private String region;

        @DateValid
        private LocalDateTime startDate;

        @DateValid
        private LocalDateTime endDate;

        // @NotNull
        // @Size(min = 1)
        private List<List<PlaceDto.Post>> places;
    }

    @Getter
    @Setter
    public static class Patch {
        private String title;
        private String content;
        private Integer memberCount;
        private String region;

        @OptionalDateValid
        private LocalDateTime startDate;

        @OptionalDateValid
        private LocalDateTime endDate;

        private List<List<PlaceDto.Post>> places;
    }
}
