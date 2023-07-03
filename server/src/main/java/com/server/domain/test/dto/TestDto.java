package com.server.domain.test.dto;

import lombok.Getter;

public class TestDto {
    @Getter
    public static class Post {
        private String message;
    }

    @Getter
    public static class Patch {
        private String message;
    }
}
