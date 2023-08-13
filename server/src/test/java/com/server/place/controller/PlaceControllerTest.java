package com.server.place.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.server.domain.place.service.PlaceService;
import com.server.domain.record.dto.RecordDto;
import com.server.domain.record.entity.Record;
import com.server.domain.record.mapper.RecordMapper;
import com.server.helper.StubData;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private PlaceService service;

    @MockBean
    private RecordMapper recordMapper;

    private static final String PLACE_DEFAULT_URL = "/api/places";

    @Test
    @Disabled // 소셜 기능 미구현
    @DisplayName("여행지 식별자로 여행일지를 조회한다.")
    void getRecordsTest() throws Exception {
        //given
        Long placeId = 1L;

        List<RecordDto.Response> responses = StubData.MockRecord.getRequestDatas("recordResponses");

        List<Record> records = new ArrayList<>();
        records.add(new Record());
        records.add(new Record());

        // given(service.findRecords(Mockito.anyLong())).willReturn(records);
        given(recordMapper.recordsToRecordResponses(Mockito.anyList())).willReturn(responses);

        String page = "1";
        String size = "10";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);

        //when
        ResultActions actions = mockMvc.perform(
            get(PLACE_DEFAULT_URL + "/{place-id}/records", placeId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .params(queryParams));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(
                MockMvcRestDocumentationWrapper.document("여행지별 여행 일지 조회",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestParameters(
                        List.of(
                            parameterWithName("page").description("Page 번호"),
                            parameterWithName("size").description("Page Size"))),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("Place")
                            .description("여행지별 여행 일지 조회")
                            .responseFields(
                                List.of(
                                    fieldWithPath("data").type(
                                            JsonFieldType.ARRAY)
                                        .description(
                                            "결과 데이터")
                                        .optional(),
                                    fieldWithPath(
                                        "data[].recordId").type(
                                            JsonFieldType.NUMBER)
                                        .description(
                                            "일지 식별자"),
                                    fieldWithPath(
                                        "data[].content").type(
                                            JsonFieldType.STRING)
                                        .description(
                                            "내용"),
                                    fieldWithPath(
                                        "data[].memberId").type(
                                            JsonFieldType.NUMBER)
                                        .description(
                                            "회원 식별자"),
                                    fieldWithPath(
                                        "data[].createdAt")
                                        .type(JsonFieldType.STRING)
                                        .description(
                                            "작성 날짜"),
                                    fieldWithPath(
                                        "data[].modifiedAt")
                                        .type(JsonFieldType.STRING)
                                        .description(
                                            "수정 날짜"),
                                    fieldWithPath(
                                        "totalElements").type(
                                            JsonFieldType.NUMBER)
                                        .description(
                                            "전체 데이터"),
                                    fieldWithPath(
                                        "totalPages").type(
                                            JsonFieldType.NUMBER)
                                        .description(
                                            "전체 페이지"),
                                    fieldWithPath(
                                        "currentPage").type(
                                            JsonFieldType.NUMBER)
                                        .description(
                                            "현재 페이지"),
                                    fieldWithPath("pageSize").type(
                                            JsonFieldType.NUMBER)
                                        .description(
                                            "페이지 크키")))
                            .build())));
    }

}
