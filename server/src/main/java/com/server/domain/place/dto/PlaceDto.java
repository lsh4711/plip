package com.server.domain.place.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

public class PlaceDto {
    @Getter
    public static class Post {
        @NotBlank
        private long apiId;

        @NotBlank
        private String name;

        @NotBlank
        private String address;

        @NotBlank
        private String latitude;

        @NotBlank
        private String longitude;

        // SchedulePlace
        @NotBlank
        private int days;

        @NotBlank
        private int orders;
    }

    @Getter
    public static class Patch {

    }
}
