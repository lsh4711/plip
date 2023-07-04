package com.server.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

import com.server.domain.member.dto.MemberDto;

public class StubData {
    public static class MockMember {
        private static Map<HttpMethod, Object> stubRequestBody;

        static {
            stubRequestBody = new HashMap<>();
            MemberDto.Post post = MemberDto.Post.builder()
                .email("test123@naver.com")
                .password("q12345678!")
                .nickname("테스트계정")
                .build();
            stubRequestBody.put(HttpMethod.POST, post);
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }
    }
}
