package com.server.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    TEST_CODE("테스트", 200),
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


