package com.server.record.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.server.domain.member.entity.Member;
import com.server.domain.record.dto.RecordDto;
import com.server.domain.record.entity.Record;
import com.server.domain.record.mapper.RecordMapper;
import com.server.domain.record.service.RecordService;
import com.server.domain.record.service.StorageService;
import com.server.global.auth.jwt.JwtTokenizer;
import com.server.helper.StubData;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private RecordService service;

    @MockBean
    private RecordMapper mapper;

    private static final String RECORD_DEFAULT_URL = "/api/records";

    @MockBean
    private StorageService storageService;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    private String accessTokenForUser;

    @BeforeAll
    public void init() {
        accessTokenForUser = StubData.MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey());
    }

    @Test
    @DisplayName("여행 일지를 등록한다.")
    void postRecordTest() throws Exception {

        //given
        Long scheduleId = 1L;
        RecordDto.Post request = (RecordDto.Post)StubData.MockRecord.getRequestBody("recordPost");
        String jsonData = gson.toJson(request);

        given(mapper.recordPostToRecord(Mockito.any(RecordDto.Post.class))).willReturn(Record.builder().build());
        given(service.createRecord(Mockito.any(Record.class), Mockito.anyLong()))
            .willReturn(Record.builder().recordId(1L).build());

        //when
        ResultActions actions = mockMvc.perform(
            post(RECORD_DEFAULT_URL + "/{schedule-place-id}", scheduleId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData));

        //then
        actions
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", is(startsWith("/api/records/"))))
            .andDo(
                MockMvcRestDocumentationWrapper.document("여행 일지 등록",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("Record")
                            .description("여행 일지 등록")
                            .requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"))
                            .responseHeaders(
                                headerWithName(HttpHeaders.LOCATION)
                                    .description("Location header. 등록된 리소드의 URI"))
                            .build())));

    }

    @Test
    @DisplayName("여행 일지를 수정한다.")
    void patchRecordTest() throws Exception {
        //given

        RecordDto.Patch request = (RecordDto.Patch)StubData.MockRecord.getRequestBody("recordPatch");
        String jsonData = gson.toJson(request);

        given(mapper.recordPatchToRecord(Mockito.any(RecordDto.Patch.class))).willReturn(
            Record.builder().recordId(1L).build());

        given(service.updateRecord(Mockito.any(Record.class))).willReturn(Record.builder().recordId(1L).build());

        RecordDto.Response response = (RecordDto.Response)StubData.MockRecord.getRequestBody("recordPatchResponse");

        given(mapper.recordToRecordResponse(Mockito.any(Record.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
            patch(RECORD_DEFAULT_URL + "/{record-id}", response.getRecordId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(
                MockMvcRestDocumentationWrapper.document("여행 일지 수정",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        List.of(parameterWithName("record-id").description("일지 식별자 ID"))),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("Record")
                            .description("여행 일지 수정")
                            .requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"))
                            .responseFields(
                                List.of(
                                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터")
                                        .optional(),
                                    fieldWithPath("data.recordId").type(JsonFieldType.NUMBER)
                                        .description("일지 식별자"),
                                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                    fieldWithPath("data.memberId").type(JsonFieldType.NUMBER)
                                        .description("회원 식별자"),
                                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING)
                                        .description("작성 날짜"),
                                    fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING)
                                        .description("수정 날짜"),
                                    fieldWithPath("data.placeName").type(JsonFieldType.STRING).description("장소 이름"),
                                    fieldWithPath("data.days").type(JsonFieldType.NUMBER).description("몇일째 여행인지")))
                            .build()

                    )));
    }

    @Test
    @DisplayName("여행 일지를 조회한다.")
    void getRecordTest() throws Exception {
        //given

        RecordDto.Response response = (RecordDto.Response)StubData.MockRecord.getRequestBody("recordResponse");

        given(service.findRecord(Mockito.anyLong())).willReturn(Record.builder().build());
        given(mapper.recordToRecordResponse(Mockito.any(Record.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
            get(RECORD_DEFAULT_URL + "/{record-id}", response.getRecordId())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(
                MockMvcRestDocumentationWrapper.document("여행 일지 조회",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        List.of(parameterWithName("record-id").description("일지 식별자 ID"))),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("Record")
                            .description("여행 일지 조회")
                            .responseFields(
                                List.of(
                                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터")
                                        .optional(),
                                    fieldWithPath("data.recordId").type(JsonFieldType.NUMBER)
                                        .description("일지 식별자"),
                                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                    fieldWithPath("data.memberId").type(JsonFieldType.NUMBER)
                                        .description("회원 식별자"),
                                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING)
                                        .description("작성 날짜"),
                                    fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING)
                                        .description("수정 날짜"),
                                    fieldWithPath("data.placeName").type(JsonFieldType.STRING).description("장소 이름"),
                                    fieldWithPath("data.days").type(JsonFieldType.NUMBER).description("몇일째 여행인지")))
                            .build())));

    }

    @Test
    @DisplayName("여행 일지 전체를 조회한다.")
    @WithMockUser(username = "user@gmail.com", password = "1234", roles = "USER")
    void getRecordsByMemberId() throws Exception {
        //given
        Member member = Member.builder().memberId(1L).build();

        Record record1 = Record.builder().recordId(1L).content("서울 롯데월드에서는...").build();
        record1.setMember(member);

        Record record2 = Record.builder().recordId(2L).content("서울 남산타워에서는...").build();
        record2.setMember(member);

        Page<Record> pageRecords = new PageImpl<>(
            List.of(record1, record2),
            PageRequest.of(0, 10,
                Sort.by("recordId").descending()),
            2);

        List<RecordDto.Response> responses = StubData.MockRecord.getRequestDatas("recordResponses");

        given(service.findAllRecords(Mockito.anyInt(), Mockito.anyInt())).willReturn(pageRecords);
        given(mapper.recordsToRecordResponses(Mockito.anyList())).willReturn(responses);

        String page = "1";
        String size = "10";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);

        //when
        ResultActions actions = mockMvc.perform(
            get(RECORD_DEFAULT_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                .contentType(MediaType.APPLICATION_JSON)
                .params(queryParams)
                .accept(MediaType.APPLICATION_JSON)

        );

        //then
        actions
            .andExpect(status().isOk())
            .andDo(
                MockMvcRestDocumentationWrapper.document("여행 일지 전체 조회",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestParameters(
                        List.of(
                            parameterWithName("page").description("Page 번호"),
                            parameterWithName("size").description("Page Size"))),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("Record")
                            .description("여행 일지 전체 조회")
                            .responseFields(
                                List.of(
                                    fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터")
                                        .optional(),
                                    fieldWithPath("data[].recordId").type(JsonFieldType.NUMBER)
                                        .description("일지 식별자"),
                                    fieldWithPath("data[].content").type(JsonFieldType.STRING)
                                        .description("내용"),
                                    fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER)
                                        .description("회원 식별자"),
                                    fieldWithPath("data[].createdAt").type(JsonFieldType.STRING)
                                        .description("작성 날짜"),
                                    fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING)
                                        .description("수정 날짜"),
                                    fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                    fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER)
                                        .description("페이지 번호"),
                                    fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER)
                                        .description("페이지 사이즈"),
                                    fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER)
                                        .description("전체 건 수"),
                                    fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER)
                                        .description("전체 페이지 수"),
                                    fieldWithPath("data[].placeName").type(JsonFieldType.STRING).description("장소 이름"),
                                    fieldWithPath("data[].days").type(JsonFieldType.NUMBER).description("몇일째 여행인지")))
                            .build())));

    }

    @Test
    @DisplayName("여행 일지를 삭제한다.")
    void deleteRecordTest() throws Exception {
        //given
        long recordId = 1L;

        doNothing().when(service).deleteRecord(recordId);
        doNothing().when(storageService).deleteImgs(anyLong(), anyLong());

        //when
        ResultActions actions = mockMvc.perform(
            delete(RECORD_DEFAULT_URL + "/{record-id}", recordId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isNoContent())
            .andDo(
                MockMvcRestDocumentationWrapper.document("여행 일지 삭제",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("record-id").description("일지 식별자 ID")),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("Record")
                            .description("여행 일지 삭제")
                            .build()))

            );

    }

    @Test
    @DisplayName("사진들을 등록한다.")
    void uploadRecordImgTest() throws Exception {
        //given
        Long recordId = 1L;

        byte[] image1Content = "image1".getBytes();
        MockMultipartFile image1 = new MockMultipartFile(
            "images", "image1.jpg", MediaType.IMAGE_JPEG_VALUE, image1Content);

        byte[] image2Content = "image2".getBytes();
        MockMultipartFile image2 = new MockMultipartFile(
            "images", "image2.jpg", MediaType.IMAGE_JPEG_VALUE, image2Content);

        List<String> indexs = List.of("0", "1", "2");

        given(storageService.store(anyList(), anyLong(), anyLong())).willReturn(indexs);

        //when
        ResultActions actions = mockMvc.perform(
            multipart(RECORD_DEFAULT_URL + "/{record-id}/img", recordId)
                .file(image1)
                .file(image2)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)

        );

        //then
        actions
            .andExpect(status().isCreated())
            .andDo(
                MockMvcRestDocumentationWrapper.document("사진 등록",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        List.of(parameterWithName("record-id").description("일지 식별자 ID"))
                    ),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("Record")
                            .description("사진 등록")
                            .responseFields(
                                fieldWithPath("data").description("사진 인덱스")

                            )
                            .build()
                    )
                )
            );

    }

    @Test
    @DisplayName("이미지 식별자로 사진을 조회한다.")
    void getRecordImgTest() throws Exception {
        //given
        Long recordId = 1L;
        Long imgId = 1L;

        String urlText = "https://jeein-bucket.s3.ap-northeast-2.amazonaws.com/record_images/5/1/0";

        given(storageService.getImg(anyLong(), anyLong(), anyLong())).willReturn(urlText);

        //when
        ResultActions actions = mockMvc.perform(
            get(RECORD_DEFAULT_URL + "/{record-id}/img/{img-id}", recordId, imgId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                .contentType(MediaType.IMAGE_JPEG)

        );
        //then
        actions
            .andExpect(status().isOk())
            .andDo(
                MockMvcRestDocumentationWrapper.document("사진 조회",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        List.of(
                            parameterWithName("record-id").description("일지 식별자 ID"),
                            parameterWithName("img-id").description("이미지 식별자 ID"))
                    ),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("Record")
                            .description("사진 조회")
                            .responseFields(
                                fieldWithPath("data").description("사진 URL")

                            )
                            .build())));

    }

    @Test
    @DisplayName("등록한 사진 전체를 조회한다.")
    @WithMockUser(username = "user@gmail.com", password = "1234", roles = "USER")
    void getRecordAllImgTest() throws Exception {
        //given
        Long recordId = 1L;

        List<String> urlTexts = List.of(
            "https://jeein-bucket.s3.ap-northeast-2.amazonaws.com/record_images/5/1/0",
            "https://jeein-bucket.s3.ap-northeast-2.amazonaws.com/record_images/5/1/1",
            "https://jeein-bucket.s3.ap-northeast-2.amazonaws.com/record_images/5/1/2"
        );

        given(storageService.getImgs(anyLong(), anyLong())).willReturn(urlTexts);

        //when
        ResultActions actions = mockMvc.perform(
            get(RECORD_DEFAULT_URL + "/{record-id}/img", recordId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                .contentType(MediaType.APPLICATION_JSON)

        );

        //then
        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.images", hasSize(3)))
            .andDo(
                MockMvcRestDocumentationWrapper.document("사진 전체 조회",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        List.of(parameterWithName("record-id").description("일지 식별자 ID"))),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("Record")
                            .description("사진 전체 조회")
                            .responseFields(
                                fieldWithPath("size").description("사진 개수"),
                                fieldWithPath("images").description("사진 URL 목록")
                            ).build())));
    }

    @Test
    @DisplayName("사진을 삭제한다.")
    void deleteRecordImgTest() throws Exception {
        //given
        long recordId = 1;
        long imgId = 1;

        doNothing().when(storageService).deleteImg(anyLong(), anyLong(), anyLong());

        //when
        ResultActions actions = mockMvc.perform(
            delete(RECORD_DEFAULT_URL + "/{record-id}/img/{img-id}", recordId, imgId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isNoContent())
            .andDo(
                MockMvcRestDocumentationWrapper.document("사진 삭제",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("record-id").description("일지 식별자 ID"),
                        parameterWithName("img-id").description("이미지 식별자 ID")),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("Record")
                            .description("사진 삭제")
                            .build()))
            );

    }
}
