package com.server.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.server.domain.member.dto.MemberDto;
import com.server.domain.place.dto.PlaceDto;
import com.server.domain.record.dto.RecordDto;
import com.server.domain.schedule.dto.ScheduleDto;
import com.server.global.auth.dto.LoginDto;
import com.server.global.auth.jwt.JwtTokenizer;

public class StubData {
    public static class MockSecurity {

        public static String getValidRefreshToken(String secretKey) {
            JwtTokenizer jwtTokenizer = new JwtTokenizer();
            Map<String, Object> claims = new HashMap<>();
            String subject = "test token";
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 1);
            Date expiration = calendar.getTime();

            String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);

            String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

            return refreshToken;
        }

        public static String getValidAccessToken(String secretKey) {
            JwtTokenizer jwtTokenizer = new JwtTokenizer();
            Map<String, Object> claims = new HashMap<>();
            claims.put("email", "test@test.com");
            claims.put("memberId", 1);
            claims.put("roles", List.of("USER"));

            String subject = "test token";
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 1);
            Date expiration = calendar.getTime();

            String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);

            String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

            return accessToken;
        }

        public static String getLogoutValidAccessToken(String secretKey) {
            JwtTokenizer jwtTokenizer = new JwtTokenizer();
            Map<String, Object> claims = new HashMap<>();
            claims.put("email", "test1234@test.com");
            claims.put("memberId", 1);
            claims.put("roles", List.of("USER"));

            String subject = "test logout token";
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
                    .username("admin")
                    .password("admin")
                    .build();

            MemberDto.Patch memberPatch = MemberDto.Patch.builder()
                    .nickname("테스트수정테스트")
                    .password("12345678!a")
                    .build();

            MemberDto.PasswordPatch memberPwPatch = MemberDto.PasswordPatch.builder()
                    .email("test123@naver.com")
                    .password("12345678a!")
                    .build();

            stubRequestBody.put("memberPost", memberPost);
            stubRequestBody.put("loginDto", loginDto);
            stubRequestBody.put("memberPatch", memberPatch);
            stubRequestBody.put("memberPwPatch", memberPwPatch);
        }

        public static Object getRequestBody(String valueName) {
            return stubRequestBody.get(valueName);
        }

        public static MemberDto.Response getSingleResponseBody() {
            return MemberDto.Response.builder()
                    .nickname("테스트수정계정")
                    .build();
        }
    }

    public static class MockRecord {
        private static Map<String, Object> stubRequestBody;
        private static Map<String, List<RecordDto.Response>> stubDatas;

        static {
            stubRequestBody = new HashMap<>();
            stubDatas = new HashMap<>();

            RecordDto.Post post = RecordDto.Post.builder()
                    .title("서울 롯데월드")
                    .content("롯데월드에서는..")
                    .build();
            stubRequestBody.put("recordPost", post);

            RecordDto.Patch patch = RecordDto.Patch.builder()
                    .title("서울 남산")
                    .content("남산에서는..")
                    .build();
            stubRequestBody.put("recordPatch", patch);

            RecordDto.Response response = RecordDto.Response.builder()
                    .recordId(1L)
                    .title("서울 롯데월드")
                    .content("롯데월드에서는..")
                    .memberId(1L)
                    .createdAt(LocalDateTime.now().withNano(0))
                    .modifiedAt(LocalDateTime.now().withNano(0))
                    .build();
            stubRequestBody.put("recordResponse", response);

            RecordDto.Response patchResponse = RecordDto.Response.builder()
                    .recordId(1L)
                    .title("서울 남산")
                    .content("남산에서는..")
                    .memberId(1L)
                    .createdAt(LocalDateTime.now().withNano(0))
                    .modifiedAt(LocalDateTime.now().withNano(0))
                    .build();
            stubRequestBody.put("recordPatchResponse", patchResponse);

            List<RecordDto.Response> responses = List.of(
                RecordDto.Response.builder()
                        .recordId(1L)
                        .title("서울 롯데월드")
                        .content("롯데월드에서는..")
                        .memberId(1L)
                        .createdAt(LocalDateTime.now().withNano(0))
                        .modifiedAt(LocalDateTime.now().withNano(0))
                        .build(),

                RecordDto.Response.builder()
                        .recordId(2L)
                        .title("서울 남산")
                        .content("남산에서는..")
                        .memberId(1L)
                        .createdAt(LocalDateTime.now().withNano(0))
                        .modifiedAt(LocalDateTime.now().withNano(0))
                        .build());

            stubDatas.put("recordResponses", responses);
        }

        public static Object getRequestBody(String valueName) {
            return stubRequestBody.get(valueName);
        }

        public static List<RecordDto.Response> getRequestDatas(String valueName) {
            return stubDatas.get(valueName);
        }

    }

    public static class MockSchedule {
        public static ScheduleDto.Post postDto = new ScheduleDto.Post();

        static {
            postDto.setTitle("제목");
            postDto.setContent("일정에 대한 메모");
            postDto.setMemberCount(1);
            postDto.setRegion("제주도");
            postDto.setStartDate(LocalDate.now());
            postDto.setEndDate(LocalDate.now().plusDays(5));
        }
    }

    public static class MockPlace {
        public static List<List<PlaceDto.Post>> postDtoLists = new ArrayList<>();

        static {
            String[] placeNames = {"감귤 농장", "초콜릿 박물관", "제주도 바닷가"};
            String[] categoryCodes = {"MT1", "CS2", "PS3"};

            for (int i = 1; i <= 2; i++) {
                List<PlaceDto.Post> postDtos = new ArrayList<>();
                for (int j = 1; j <= 3; j++) {
                    PlaceDto.Post postDto = new PlaceDto.Post();
                    postDto.setApiId(j * 10 + j);
                    postDto.setName(placeNames[j - 1]);
                    postDto.setAddress("제주도 무슨동 무슨길" + j);
                    postDto.setLatitude(String.format("%d.%d", j * 205 + j * 17 + j * 8, j * 27));
                    postDto.setLongitude(String.format("%d.%d", j * 121 + j * 23 + j * 3, j * 31));
                    postDto.setCategory(categoryCodes[j - 1]);
                    postDtos.add(postDto);
                }
                postDtoLists.add(postDtos);
            }

        }
    }
}
