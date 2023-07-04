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

        // schedulePlace
        @NotBlank
        private int day;

        @NotBlank
        private int order;
    }

    @Getter
    public static class Patch {

    }
}
