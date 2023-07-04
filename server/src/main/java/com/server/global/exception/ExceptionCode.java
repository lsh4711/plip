package com.server.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    TEST_CODE("테스트", 200),
    REFRESH_TOKEN_NOT_FOUND("RefreshToken not found", 404),
    EXPIRED_TOKEN("Token has expired", 401),
    SIGNATURE_INVALID("Invalid jwt signature", 401),
    ACCESS_DENIED("This account is inaccessible", 401),
    UNAUTHORIZED("invalid token Data", 401),
    MEMBER_NOT_FOUND("Member not found", 404),
    EMAIL_EXISTS("Email exists", 409),
    AUTH_MAIL_CODE_NOT_FOUND("Auth mail code not found", 404),
    AUTH_MAIL_CODE_MISMATCH("Auth mail code is mismatch", 403),
    NICKNAME_EXISTS("Nickname exists", 409),
    MAIL_SEND_FAIL("Send mail fail", 500);

    private String message;
    private int statusCode;

    ExceptionCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
