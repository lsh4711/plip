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
    NICKNAME_EXISTS("Nickname exists", 409),
    MAIL_SEND_FAIL("Send mail fail", 500),

    RECORD_NOT_FOUND("여행 일지를 찾을 수 없습니다.",404 ),
    RECORD_EXISTS("등록된 여행 일지가 이미 존재합니다.",409 ),
    NOT_IMPLEMENTATION("Not Implementation", 501);

    @Getter

    private String message;

    @Getter
    private int statusCode;

    ExceptionCode(String message,int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}


