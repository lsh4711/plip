package com.server.domain.record.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class RecordDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private String title;
        private String content;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long recordId;
        private String title;
        private String content;
        private Long memberId;
        private LocalDate createdAt;
        private LocalDate modifiedAt;

    }
}
