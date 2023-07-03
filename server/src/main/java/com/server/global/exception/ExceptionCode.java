package com.server.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    TEST_CODE("테스트", 200);

    private String message;
    private int statusCode;

    ExceptionCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
