package com.server.global.exception;

import lombok.Getter;

public enum ExceptionCode {

    RECORD_NOT_FOUND(404, "여행 일지를 찾을 수 없습니다."),
    RECORD_EXISTS(409, "등록된 여행 일지가 이미 존재합니다."),
    NOT_IMPLEMENTATION(501, "Not Implementation");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

