package com.server.domain.schedule.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
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
        private int memberCount; // optional, default: 1

        @NotBlank
        private String region;

        @DateValid
        private LocalDate startDate;

        @DateValid
        private LocalDate endDate;

        // @NotNull
        // @Size(min = 1)
        private List<List<PlaceDto.@Valid Post>> places;
    }

    @Getter
    @Setter
    public static class Patch {
        private String title;
        private Integer memberCount;
        private String region;

        @OptionalDateValid
        private LocalDate startDate;

        @OptionalDateValid
        private LocalDate endDate;

        private List<List<PlaceDto.@Valid Post>> places;
    }
}
