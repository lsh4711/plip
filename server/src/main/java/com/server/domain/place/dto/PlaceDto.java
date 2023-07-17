package com.server.domain.place.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

        @Pattern(regexp = "^\\d{2,5}-?\\d{3,4}-?\\d{4}$") // 테스트 필요
        private String phone;

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
