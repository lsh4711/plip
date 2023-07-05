package com.server.domain.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class AuthMailCodeDto {
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Post {
        private String authCode;
        private String email;
    }
}
