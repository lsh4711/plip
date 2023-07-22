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
import com.server.domain.member.entity.Member;
import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.dto.PlaceResponse;
import com.server.domain.record.dto.RecordDto;
import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.dto.ScheduleResponse;
import com.server.domain.schedule.entity.Schedule;
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
                    .memberId(1L)
                    .email("testtest@naver.com")
                    .nickname("테스트수정계정")
                    .build();
        }
    }

    public static class MockRecord {
        private static Map<String, Object> stubRequestBody;
        private static Map<String, List<RecordDto.Response>> stubDatas;

        public static Map<Long, List<RecordDto.Response>> recordResponseMap;

        static {
            stubRequestBody = new HashMap<>();
            stubDatas = new HashMap<>();

            RecordDto.Post post = RecordDto.Post.builder()
                    .content("롯데월드에서는..")
                    .build();
            stubRequestBody.put("recordPost", post);

            RecordDto.Patch patch = RecordDto.Patch.builder()
                    .content("남산에서는..")
                    .build();
            stubRequestBody.put("recordPatch", patch);

            RecordDto.Response response = RecordDto.Response.builder()
                    .recordId(1L)
                    .content("롯데월드에서는..")
                    .memberId(1L)
                    .createdAt(LocalDateTime.now().withNano(0))
                    .modifiedAt(LocalDateTime.now().withNano(0))
                    .build();
            stubRequestBody.put("recordResponse", response);

            RecordDto.Response patchResponse = RecordDto.Response.builder()
                    .recordId(1L)
                    .content("남산에서는..")
                    .memberId(1L)
                    .createdAt(LocalDateTime.now().withNano(0))
                    .modifiedAt(LocalDateTime.now().withNano(0))
                    .build();
            stubRequestBody.put("recordPatchResponse", patchResponse);

            List<RecordDto.Response> responses = List.of(
                RecordDto.Response.builder()
                        .recordId(1L)
                        .content("롯데월드에서는..")
                        .memberId(1L)
                        .createdAt(LocalDateTime.now().withNano(0))
                        .modifiedAt(LocalDateTime.now().withNano(0))
                        .build(),

                RecordDto.Response.builder()
                        .recordId(2L)
                        .content("남산에서는..")
                        .memberId(1L)
                        .createdAt(LocalDateTime.now().withNano(0))
                        .modifiedAt(LocalDateTime.now().withNano(0))
                        .build());

            stubDatas.put("recordResponses", responses);

            String[] placeNames = {"감귤 농장", "초콜릿 박물관", "제주도 바닷가"};
            recordResponseMap = new HashMap<>();

            for (long i = 4; i <= 9; i++) {
                List<RecordDto.Response> recordResponses = new ArrayList<>();
                RecordDto.Response recordResponse = RecordDto.Response.builder()
                        .recordId(i - 3)
                        .content("여행 일지 내용")
                        .memberId(1L)
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .placeName(placeNames[((int)i - 1) % 3])
                        .days(((int)i - 1) / 3)
                        .build();
                recordResponses.add(recordResponse);
                recordResponseMap.put(i, recordResponses);
            }
        }

        public static Object getRequestBody(String valueName) {
            return stubRequestBody.get(valueName);
        }

        public static List<RecordDto.Response> getRequestDatas(String valueName) {
            return stubDatas.get(valueName);
        }

    }

    public static class MockSchedule {
        public static Schedule schedule = new Schedule();
        public static List<Schedule> schedules = new ArrayList<>();

        public static ScheduleDto.Post postDto = new ScheduleDto.Post();

        public static ScheduleDto.Patch patchDto = new ScheduleDto.Patch();

        public static ScheduleResponse scheduleResponse = new ScheduleResponse();

        static {
            LocalDate now = LocalDate.now();

            // Schedule
            Member member = Member.builder()
                    .memberId(1L)
                    .nickname("관리자")
                    .build();
            schedule.setScheduleId(1L);
            schedule.setMember(member);
            schedule.setSchedulePlaces(new ArrayList<>());

            for (long i = 1; i <= 3; i++) {
                Schedule newSchedule = new Schedule();
                newSchedule.setScheduleId(i);
                newSchedule.setMember(member);
                newSchedule.setSchedulePlaces(new ArrayList<>());
                schedules.add(newSchedule);
            }

            // ScheduleDto.Post
            postDto.setRegion("seoul");
            postDto.setStartDate(now);
            postDto.setEndDate(now.plusDays(2));

            // ScheduleDto.Patch

            patchDto.setTitle("일정 제목");
            patchDto.setMemberCount(1);
            patchDto.setRegion("seoul");
            patchDto.setStartDate(now);
            patchDto.setEndDate(now.plusDays(2));

            // ScheduleResponse
            scheduleResponse.setMemberId(1L);
            scheduleResponse.setNickname("닉네임");
            scheduleResponse.setScheduleId(1L);
            scheduleResponse.setRegion("seoul");
            scheduleResponse.setKorRegion("서울");
            scheduleResponse.setTitle("일정 제목");
            scheduleResponse.setMemberCount(1);
            scheduleResponse.setStartDate(now.atStartOfDay());
            scheduleResponse.setEndDate(now.plusDays(2).atStartOfDay());
            scheduleResponse.setPeriod(3);
            scheduleResponse.setCreatedAt(LocalDateTime.now());
            scheduleResponse.setModifiedAt(LocalDateTime.now());
            scheduleResponse.setPlaceSize(6);
        }
    }

    public static class MockPlace {
        public static List<List<PlaceDto.Patch>> patchDtoLists = new ArrayList<>();
        public static List<List<PlaceResponse>> placeResponseLists = new ArrayList<>();

        static {
            String[] placeNames = {"감귤 농장", "초콜릿 박물관", "제주도 바닷가"};
            String[] categoryCodes = {"MT1", "CS2", "PS3"};
            String[] categoryNames = {"대형마트", "편의점", "어린이집, 유치원"};

            for (int i = 1; i <= 2; i++) {
                List<PlaceDto.Patch> patchDtos = new ArrayList<>();
                List<PlaceResponse> placeResponses = new ArrayList<>();
                for (int j = 1; j <= 3; j++) {
                    // PlaceDto.Patch
                    PlaceDto.Patch patchDto = new PlaceDto.Patch();
                    patchDto.setPlaceId((long)j);
                    patchDto.setSchedulePlaceId(i * 3L + j);
                    patchDto.setApiId(j * 10L + j);
                    patchDto.setName(placeNames[j - 1]);
                    patchDto.setAddress("제주도 무슨동 무슨길" + j);
                    patchDto.setPhone("010-0000-0000");
                    patchDto.setLatitude(String.format("%d.%d", j * 205 + j * 17 + j * 8, j * 27));
                    patchDto.setLongitude(String.format("%d.%d", j * 121 + j * 23 + j * 3, j * 31));
                    patchDto.setCategory(categoryCodes[j - 1]);
                    patchDtos.add(patchDto);

                    // PlaceResponse
                    PlaceResponse placeResponse = new PlaceResponse();
                    placeResponse.setPlaceId((long)j);
                    placeResponse.setScheduleId(1);
                    placeResponse.setSchedulePlaceId(i * 3L + j);
                    placeResponse.setApiId(j * 10L + j);
                    placeResponse.setName(placeNames[j - 1]);
                    placeResponse.setAddress("제주도 무슨동 무슨길" + j);
                    placeResponse.setPhone("010-0000-0000");
                    placeResponse.setLatitude(String.format("%d.%d", j * 205 + j * 17 + j * 8, j * 27));
                    placeResponse.setLongitude(String.format("%d.%d", j * 121 + j * 23 + j * 3, j * 31));
                    placeResponse.setCategory(categoryCodes[j - 1]);
                    placeResponse.setCategoryName(categoryNames[j - 1]);
                    placeResponse.setDays(i);
                    placeResponse.setOrders(j);
                    placeResponses.add(placeResponse);
                }
                patchDtoLists.add(patchDtos);
                placeResponseLists.add(placeResponses);
            }

        }
    }
}
