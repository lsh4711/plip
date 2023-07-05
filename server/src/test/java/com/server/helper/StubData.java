package com.server.helper;

<<<<<<< HEAD
=======

import java.time.LocalDateTime;

import java.util.Calendar;
import java.util.Date;


>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

import com.server.domain.member.dto.MemberDto;
<<<<<<< HEAD

public class StubData {
    public static class MockMember {
        private static Map<HttpMethod, Object> stubRequestBody;

        static {
            stubRequestBody = new HashMap<>();
            MemberDto.Post post = MemberDto.Post.builder()
=======
import com.server.domain.record.dto.RecordDto;

import com.server.global.auth.dto.LoginDto;
import com.server.global.auth.jwt.JwtTokenizer;



public class StubData {

    public static class MockSecurity {
        public static String getValidAccessToken(String secretKey, String role) {
            JwtTokenizer jwtTokenizer = new JwtTokenizer();
            Map<String, Object> claims = new HashMap<>();
            claims.put("email", "test@test.com");
            claims.put("role", role);

            String subject = "test access token";
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 1);
            Date expiration = calendar.getTime();

            String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);

            String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

            return accessToken;
        }
    }

    public static class MockMember {
        private static Map<String, Object> stubRequestBody;

        static {
            stubRequestBody = new HashMap<>();
            MemberDto.Post memberPost = MemberDto.Post.builder()
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
                .email("test123@naver.com")
                .password("q12345678!")
                .nickname("테스트계정")
                .build();
<<<<<<< HEAD
            stubRequestBody.put(HttpMethod.POST, post);
=======

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
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }
    }
}
