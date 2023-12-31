package com.server.domain.record.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RecordDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String content;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private Long recordId;
        private String content;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long recordId;
        private String content;
        private Long memberId;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        // 클라이언트 요청
        private String placeName;
        private Integer days;
    }
}
