package com.server.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.server.domain.member.dto.MemberDto;
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
