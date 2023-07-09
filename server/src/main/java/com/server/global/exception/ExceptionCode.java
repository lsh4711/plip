package com.server.global.exception;

import lombok.Getter;

public enum ExceptionCode {
    TEST_CODE("테스트", 200),
    NOT_IMPLEMENTATION("Not Implementation", 501),

    // 인증
    SIGNATURE_INVALID("Invalid jwt signature", 401),
    ACCESS_DENIED("This account is inaccessible", 401),
    UNAUTHORIZED("invalid token Data", 401),
    EXPIRED_TOKEN("Token has expired", 401),
    AUTH_MAIL_CODE_MISMATCH("Auth mail code is mismatch", 403),
    AUTH_MAIL_CODE_NOT_FOUND("Auth mail code not found", 404),
    REFRESH_TOKEN_NOT_FOUND("RefreshToken not found", 404),
    MAIL_SEND_FAIL("Send mail fail", 500),
    NON_REGISTERED_USER("The email address is not registered.",400),

    // 회원
    MEMBER_NOT_FOUND("Member not found", 404),
    EMAIL_EXISTS("Email exists", 409),
    NICKNAME_EXISTS("Nickname exists", 409),

    // 일정
    SCHEDULE_NOT_FOUND("Schedule Not Found.", 404),

    // 일지
    CANNOT_CHANGE_RECORD("No modify/delete permissions for this record", 403),
    RECORD_NOT_FOUND("Record Not Found.", 404),
    RECORD_EXISTS("Record already exists.", 409),

    // 일정_장소
    SCHEDULE_PLACE_NOT_FOUND("Schedule Place Not Found.", 404),

    END("마무리", 200);

    @Getter
    private String message;

    @Getter
    private int statusCode;

    ExceptionCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
