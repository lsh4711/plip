package com.server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberDto {
    private MemberDto() {
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Post {
        /**
         * TODO: 이메일은 이메일 형식 검증 필요,
         *       닉네임은 문자, 숫자로만 이루어져 있으며 2글자 이상 8글자 이하
         *       비밀번호는 8자리 이상 영문, 숫자, 특문을 합쳐야 한다.
         * */
        private String email;
        private String password;
        private String nickname;
    }
}
