package com.server.global.exception;

import lombok.Getter;

public enum ExceptionCode {
    TEST_CODE("테스트", 200),
    LOG_NOT_FOUND("로그 파일이 존재하지 않습니다.", 404),
    INTERNAL_SERVER_ERROR("Internal Server Error", 500),
    NOT_IMPLEMENTATION("Not Implementation", 501),

    // 인증
    SIGNATURE_INVALID("잘못된 JWT 서명입니다.", 401),
    TOKEN_FORMAT_INVALID("올바르지 않는 토큰입니다.", 401),
    AUTH_MAIL_CODE_MISMATCH("인증코드가 올바르지 않습니다.", 403),
    AUTH_MAIL_CODE_NOT_FOUND("발급받은 인증코드를 찾을 수 없습니다. 인증코드를 다시 받아주세요.", 404),
    REFRESH_TOKEN_NOT_FOUND("토큰을 찾을 수 없거나 만료됐습니다. 다시 로그인해주세요.", 401),
    ACCESS_TOKEN_NOT_FOUND("엑세스 토큰을 찾을 수 없습니다.", 401),
    MAIL_SEND_FAIL("Send mail fail", 500),
    NON_REGISTERED_USER("회원가입하지 않은 이메일입니다.", 400),
    PASSWORD_INVALID("비밀번호가 맞지 않습니다.", 400),
    UNKNOWN_USER("알 수 없는 사용자입니다.", 400),
    LOGOUT_USER("로그아웃된 유저입니다.", 401),
    FORBIDDEN("권한이 없습니다.", 403),

    // 회원
    MEMBER_NOT_FOUND("해당 회원을 찾을 수 없습니다.", 404),
    EMAIL_EXISTS("이미 존재하는 이메일입니다.", 409),
    NICKNAME_EXISTS("이미 존재하는 닉네임입니다.", 409),

    // 일정
    SCHEDULE_NOT_FOUND("일정이 존재하지 않거나 권한이 없습니다.", 404),

    // 장소
    NO_VISITOR("방문객이 없습니다.", 404),
    EMPTY_PLACES("입력된 여행지가 없습니다.", 400),

    // 일지
    CANNOT_ACCESS_RECORD("Unauthorized to access the record", 403),
    CANNOT_CHANGE_RECORD("No modify/delete permissions for this record", 403),
    RECORD_NOT_FOUND("Record Not Found.", 404),

    // 이미지
    IMAGE_NOT_FOUND("Image Not Found.", 404),

    // 일정_장소
    SCHEDULE_PLACE_NOT_FOUND("여행지가 존재하지 않거나 권한이 없습니다.", 404),

    // 알림
    TASK_NOT_FOUND("예약된 작업이 없습니다.", 404),
    TASK_EXISTS("해당 id로 예약된 작업이 있습니다.", 409),
    PUSH_FAILD("푸시 전송에 실패했습니다.", 500),

    // 공유
    SHARE_CODE_INVALID("코드가 일치하지 않습니다.", 400),

    // 토큰(이벤트) - 임시
    EVENT_TOKENS_NOT_FOUND("푸시 알림 허용 후 카카오로 로그인해주세요!", 400),
    EVENT_PUSH_TOKEN_NOT_FOUND("푸시 알림 허용 후 재로그인 해주세요!", 400),
    EVENT_KAKAO_TOKEN_NOT_FOUND("카카오로 로그인 해주세요!", 400),

    END("마무리", 200);

    @Getter
    private final String message;

    @Getter
    private final int statusCode;

    ExceptionCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
