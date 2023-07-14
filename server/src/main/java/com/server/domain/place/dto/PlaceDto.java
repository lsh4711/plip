package com.server.domain.place.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class PlaceDto {
    @Getter
    @Setter
    public static class Post {
        // @NotBlank // optional
        private long apiId;

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

        @NotNull
        private Boolean bookmark;
    }

    @Getter
    public static class Patch {

    }
}
