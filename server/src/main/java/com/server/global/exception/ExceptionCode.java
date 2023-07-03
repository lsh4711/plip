package com.server.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    TEST_CODE("테스트", 200),
    EMAIL_EXISTS("email exists", 409),
    NICKNAME_EXISTS("nickname exists", 409),
    MAIL_SEND_FAIL("send mail fail", 500);
    private String message;
    private int statusCode;

    ExceptionCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
