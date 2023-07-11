package com.server.domain.place.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class PlaceDto {
    @Getter
    @Setter
    public static class Post {
        @NotBlank // optional
        private long apiId = 0;

        @NotBlank
        private String name;

        @NotBlank
        private String address;

        @NotBlank
        private String latitude;

        @NotBlank
        private String longitude;

        @NotBlank
        private String category;
    }

    @Getter
    public static class Patch {

    }
}
