package com.server.helper;

import java.util.HashMap;
import java.util.Map;

import com.server.domain.member.dto.MemberDto;
import com.server.global.auth.dto.LoginDto;

public class StubData {
    public static class MockMember {
        private static Map<String, Object> stubRequestBody;

        static {
            stubRequestBody = new HashMap<>();
            MemberDto.Post memberPost = MemberDto.Post.builder()
                .email("test123@naver.com")
                .password("q12345678!")
                .nickname("테스트계정")
                .build();

            LoginDto loginDto = LoginDto.builder()
                .username("test123@naver.com")
                .password("q12345678@")
                .build();
            stubRequestBody.put("memberPost", memberPost);
            stubRequestBody.put("loginDto", loginDto);
        }

        public static Object getRequestBody(String valueName) {
            return stubRequestBody.get(valueName);
        }
    }
}
