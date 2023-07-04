package com.server.domain.record.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class RecordDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Post {
        private String title;
        private String content;

    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long recordId;
        private String title;
        private String content;
        private Long memberId;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

    }
}
