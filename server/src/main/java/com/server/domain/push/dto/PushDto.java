package com.server.domain.push.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class PushDto {
    @Getter
    @Setter
    public static class Post {
        @NotBlank
        private String pushToken;
    }

    @Getter
    @Setter
    public static class Patch {
        private String pushToken;
    }
}
