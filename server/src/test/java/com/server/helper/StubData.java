package com.server.helper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

import com.server.domain.member.dto.MemberDto;
import com.server.domain.record.dto.RecordDto;

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

    public static class MockRecord{
        private static Map<HttpMethod, Object> stubRequestBody;

        static{
            stubRequestBody = new HashMap<>();
            RecordDto.Post post = RecordDto.Post.builder()
                .title("서울 롯데월드")
                .content("롯데월드에서는..")
                .build();
            stubRequestBody.put(HttpMethod.POST, post);

            RecordDto.Response response = RecordDto.Response.builder()
                .recordId(1L)
                .title("서울 롯데월드")
                .content("롯데월드에서는..")
                .memberId(1L)
                .createdAt(LocalDateTime.now().withNano(0))
                .modifiedAt(LocalDateTime.now().withNano(0))
                .build();
            stubRequestBody.put(HttpMethod.GET, response);
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }
    }
}
