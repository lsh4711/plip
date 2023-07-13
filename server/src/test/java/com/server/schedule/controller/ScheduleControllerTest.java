package com.server.schedule.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

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

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.entity.Place;
import com.server.domain.place.mapper.PlaceMapper;
import com.server.domain.place.service.PlaceService;
import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.service.SchedulePlaceService;
import com.server.domain.schedule.service.ScheduleService;
import com.server.global.auth.jwt.JwtTokenizer;
import com.server.helper.LocalDateAdapter;
import com.server.helper.StubData;
import com.server.helper.StubData.MockPlace;
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
    private PlaceMapper placeMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    // @Autowired
    private Gson gson = new GsonBuilder()
            // .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
            .create();

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private PlaceService placeService;

    @MockBean
    private SchedulePlaceService schedulePlaceService;

    private String token;

    @BeforeAll
    public void init() {
        token = StubData.MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey());
    }

    @Test
    @DisplayName("일정 등록")
    void postScheduleTest() throws Exception {
        // given
        ScheduleDto.Post postDto = MockSchedule.postDto;
        List<List<PlaceDto.Post>> placeDtoLists = MockPlace.postDtoLists;
        postDto.setPlaces(placeDtoLists);
        String requestBody = gson.toJson(postDto);

        // List<List<Place>> placeLists = placeMapper.postDtoListsToPlaceLists(placeDtoLists);
        Schedule schedule = new Schedule();
        schedule.setScheduleId(1L);

        given(scheduleService.saveSchedule(Mockito.any(Schedule.class))).willReturn(schedule);
        given(placeService.savePlaceLists(Mockito.any(Schedule.class), Mockito.<List<Place>>anyList()))
                .willReturn(null);
        // given(schedulePlaceSedrvice.saveSchedulePlaces(Mockito.<SchedulePlace>anyList())).willReturn(null);

        // when
        ResultActions actions = mockMvc.perform(
            post(BASE_URL + "/write")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                    is(startsWith("/api/schedules"))))
                .andDo(
                    document("일정 등록",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                    .description("일정 등록")
                                    .requestFields(List.of())
                                    .responseFields(List.of())
                                    .build())));
    }

    @Test
    @DisplayName("일정 수정")
    void patchScheduleTest() throws Exception {
    }

    @Test
    @DisplayName("일정 조회")
    void getScheduleTest() throws Exception {
    }

    @Test
    @DisplayName("일정의 여행지 조회")
    void getPlacesByScheduleIdTest() throws Exception {
    }

    @Test
    @DisplayName("일정 삭제")
    void deleteScheduleTest() throws Exception {
    }
}
