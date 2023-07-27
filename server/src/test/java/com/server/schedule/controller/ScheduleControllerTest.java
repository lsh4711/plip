package com.server.schedule.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.domain.mail.service.MailService;
import com.server.domain.member.service.MemberService;
import com.server.domain.oauth.service.KakaoApiService;
import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.dto.PlaceResponse;
import com.server.domain.place.entity.Place;
import com.server.domain.place.mapper.PlaceMapper;
import com.server.domain.place.service.PlaceService;
import com.server.domain.push.service.PushService;
import com.server.domain.record.dto.RecordDto;
import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.dto.ScheduleResponse;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;
import com.server.domain.schedule.mapper.ScheduleMapper;
import com.server.domain.schedule.mapper.SchedulePlaceMapper;
import com.server.domain.schedule.service.SchedulePlaceService;
import com.server.domain.schedule.service.ScheduleService;
import com.server.global.auth.jwt.JwtTokenizer;
import com.server.helper.LocalDateAdapter;
import com.server.helper.StubData;
import com.server.helper.StubData.MockPlace;
import com.server.helper.StubData.MockRecord;
import com.server.helper.StubData.MockSchedule;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScheduleControllerTest {
    private static final String BASE_URL = "/api/schedules";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    // @Autowired
    private final Gson gson = new GsonBuilder()
            // .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    @MockBean
    private MemberService memberService;

    @MockBean
    private ScheduleMapper scheduleMapper;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private PlaceService placeService;

    @MockBean
    private PlaceMapper placeMapper;

    @MockBean
    private SchedulePlaceService schedulePlaceService;

    @MockBean
    private SchedulePlaceMapper schedulePlaceMapper;

    @MockBean
    private PushService pushService;

    @MockBean
    private KakaoApiService kakaoApiService;

    @MockBean
    private MailService mailService;

    private String token;

    @BeforeAll
    public void init() {
        token = StubData.MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey());
    }

    @Test
    @DisplayName("비어있는 여행 일정 등록")
    void postScheduleTest() throws Exception {
        // given
        ScheduleDto.Post postDto = MockSchedule.postDto;
        String requestBody = gson.toJson(postDto);

        Schedule schedule = MockSchedule.schedule;

        given(scheduleMapper.postDtoToSchedule(Mockito.any(ScheduleDto.Post.class))).willReturn(new Schedule());
        given(scheduleService.saveSchedule(Mockito.any(Schedule.class))).willReturn(schedule);
        doNothing().when(pushService).sendPostScheduleMessage(Mockito.any(Schedule.class));
        doNothing().when(kakaoApiService).sendPostScheduleMessage(Mockito.any(Schedule.class));
        doNothing().when(mailService).sendPostScheduleMail(Mockito.any(Schedule.class));

        // when
        ResultActions actions = mockMvc.perform(
            post(BASE_URL + "/write")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                    is(String.format("%s/%d", BASE_URL, schedule.getScheduleId()))))
                .andDo(
                    document("비어있는 여행 일정 등록",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                    .tag("Schedule")
                                    .description("비어있는 여행 일정 등록")
                                    // .requestFields(List.of())
                                    // .responseFields(List.of())
                                    .build())));
    }

    @Test
    @DisplayName("여행 일정 수정")
    void patchScheduleTest() throws Exception {
        // given
        List<List<PlaceDto.Patch>> placeDtoLists = MockPlace.patchDtoLists;
        ScheduleDto.Patch patchDto = MockSchedule.patchDto;
        patchDto.setPlaces(placeDtoLists);

        String requestBody = gson.toJson(patchDto);

        // Schedule
        Schedule schedule = MockSchedule.schedule;

        given(scheduleMapper.patchDtoToSchedule(Mockito.any(ScheduleDto.Patch.class),
            Mockito.anyLong())).willReturn(new Schedule());
        given(scheduleService.updateSchedule(Mockito.any(Schedule.class))).willReturn(schedule);

        // Place
        given(placeMapper.patchDtoListsToPlaceLists(Mockito.<List<PlaceDto.Patch>>anyList()))
                .willReturn(new ArrayList<>());
        given(placeService.savePlaceLists(Mockito.<List<Place>>anyList())).willReturn(new ArrayList<>());

        // SchedulePlace
        given(placeMapper.placesToSchedulePlaces(Mockito.<Place>anyList(),
            Mockito.any(Schedule.class))).willReturn(new ArrayList<>());
        given(schedulePlaceService.updateSchedulePlaces(Mockito.<SchedulePlace>anyList(),
            Mockito.<SchedulePlace>anyList())).willReturn(new ArrayList<>());

        // ScheduleResponse
        ScheduleResponse scheduleResponse = MockSchedule.scheduleResponse;
        List<List<PlaceResponse>> placeResponseLists = MockPlace.placeResponseLists;

        given(schedulePlaceMapper.schedulePlacesToPlaceResponseLists(Mockito.any(Schedule.class)))
                .willReturn(placeResponseLists);
        given(scheduleMapper.scheduleToScheduleResponse(Mockito.any(Schedule.class)))
                .willReturn(scheduleResponse);

        // when
        ResultActions actions = mockMvc.perform(
            patch(BASE_URL + "/{scheduleId}/edit", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                    document("여행 일정 수정",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                    .tag("Schedule")
                                    .description("여행 일정 수정")
                                    // .requestFields(List.of())
                                    // .responseFields(List.of())
                                    .build())));
    }

    @Test
    @DisplayName("여행 일정 조회")
    void getScheduleTest() throws Exception {
        // given
        // Schedule
        Schedule schedule = MockSchedule.schedule;

        given(scheduleService.findScheduleOfAuthMember(Mockito.anyLong())).willReturn(schedule);

        // ScheduleResponse
        ScheduleResponse scheduleResponse = MockSchedule.scheduleResponse;
        List<List<PlaceResponse>> placeResponseLists = MockPlace.placeResponseLists;

        given(schedulePlaceMapper.schedulePlacesToPlaceResponseLists(Mockito.any(Schedule.class)))
                .willReturn(placeResponseLists);
        given(scheduleMapper.scheduleToScheduleResponse(Mockito.any(Schedule.class)))
                .willReturn(scheduleResponse);

        // RecordResponseMap
        Map<Long, List<RecordDto.Response>> map = MockRecord.recordResponseMap;

        given(schedulePlaceMapper.toRecordResponseMap(Mockito.<SchedulePlace>anyList()))
                .willReturn(map);

        // when
        ResultActions actions = mockMvc.perform(
            get(BASE_URL + "/{scheduleId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                    document("여행 일정 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                    .tag("Schedule")
                                    .description("여행 일정 조회")
                                    // .requestFields(List.of())
                                    // .responseFields(List.of())
                                    .build())));
    }

    @Test
    @DisplayName("회원의 모든 일정 조회")
    void getSchedulesTest() throws Exception {
        // given
        // Schedule
        List<Schedule> schedules = MockSchedule.schedules;

        given(scheduleService.findSchedulesOfAuthMember()).willReturn(schedules);

        // ScheduleResponse
        ScheduleResponse scheduleResponse = MockSchedule.scheduleResponse;
        List<List<PlaceResponse>> placeResponseLists = MockPlace.placeResponseLists;

        given(schedulePlaceMapper.schedulePlacesToPlaceResponseLists(Mockito.any(Schedule.class)))
                .willReturn(placeResponseLists);
        given(scheduleMapper.scheduleToScheduleResponse(Mockito.any(Schedule.class)))
                .willReturn(scheduleResponse);

        // when
        ResultActions actions = mockMvc.perform(
            get(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                    document("회원의 모든 일정 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                    .tag("Schedule")
                                    .description("회원의 모든 일정 조회")
                                    // .requestFields(List.of())
                                    // .responseFields(List.of())
                                    .build())));
    }

    @Test
    @DisplayName("공유된 일정 조회")
    void getSharedScheduleTest() throws Exception {
        // given
        // Schedule
        Schedule schedule = MockSchedule.schedule;

        given(scheduleService.findSharedSchedule(Mockito.anyLong(),
            Mockito.anyLong(), Mockito.anyString())).willReturn(schedule);

        // ScheduleResponse
        ScheduleResponse scheduleResponse = MockSchedule.scheduleResponse;
        List<List<PlaceResponse>> placeResponseLists = MockPlace.placeResponseLists;

        given(schedulePlaceMapper.schedulePlacesToPlaceResponseLists(Mockito.any(Schedule.class)))
                .willReturn(placeResponseLists);
        given(scheduleMapper.scheduleToScheduleResponse(Mockito.any(Schedule.class)))
                .willReturn(scheduleResponse);

        // when
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("id", "1");
        queryParams.add("code", "codecodecodecode ");

        ResultActions actions = mockMvc.perform(
            get(BASE_URL + "/{scheduleId}/share", 1)
                    .queryParams(queryParams)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                    document("공유된 일정 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                    .tag("Schedule")
                                    .description("공유된 일정 조회")
                                    // .requestFields(List.of())
                                    // .responseFields(List.of())
                                    .build())));
    }

    @Test
    @DisplayName("여행 일정의 여행지 조회")
    void getPlacesByScheduleIdTest() throws Exception {
        // given
        // Schedule
        Schedule schedule = MockSchedule.schedule;

        given(scheduleService.findScheduleOfAuthMember(Mockito.anyLong())).willReturn(schedule);

        // ScheduleResponse
        List<List<PlaceResponse>> placeResponseLists = MockPlace.placeResponseLists;

        given(schedulePlaceMapper.schedulePlacesToPlaceResponseLists(Mockito.any(Schedule.class)))
                .willReturn(placeResponseLists);

        // when
        ResultActions actions = mockMvc.perform(
            get(BASE_URL + "/{scheduleId}/places", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                    document("여행 일정의 여행지 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                    .tag("Schedule")
                                    .description("여행 일정의 여행지 조회")
                                    // .requestFields(List.of())
                                    // .responseFields(List.of())
                                    .build())));
    }

    @Test
    @DisplayName("여행 일정 삭제")
    void deleteScheduleTest() throws Exception {
        // given4
        given(scheduleService.deleteSchedule(Mockito.anyLong())).willReturn(null);

        // when
        ResultActions actions = mockMvc.perform(
            delete(BASE_URL + "/{scheduleId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token));

        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(
                    document("여행 일정 삭제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                    .tag("Schedule")
                                    .description("여행 일정 삭제")
                                    // .requestFields(List.of())
                                    // .responseFields(List.of())
                                    .build())));
    }
}
