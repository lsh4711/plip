package com.server.domain.place.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

public class PlaceDto {
    @Getter
    @Setter
    public static class Patch {
        private Long placeId; // 새로 추가된 장소는 값이 없음
        private Long schedulePlaceId; // 새로 추가된 장소는 값이 없음

        @NotNull
        private Long apiId;

        @NotBlank
        private String name;

        @NotBlank
        private String address;

        @NotNull
        @Pattern(regexp = "|^\\d{2,5}(-\\d{3,4})?(-\\d{4})?$")
        private String phone; // 빈 문자열로도 옴

        @NotBlank
        private String latitude;

        @NotBlank
        private String longitude;

        @NotNull
        private String category; // 빈 문자열로도 옴

        // @NotNull
        // private Boolean bookmark;
    }
}
