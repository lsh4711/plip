package com.server.record.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.server.domain.record.ImageManager;
import com.server.domain.record.controller.RecordController;

import com.server.domain.record.dto.RecordDto;
import com.server.domain.record.entity.Record;
import com.server.domain.record.mapper.RecordMapper;
import com.server.domain.record.service.RecordService;
import com.server.global.config.SecurityConfig;
import com.server.helper.StubData;

import org.springframework.mock.web.MockMultipartFile;


@Import({SecurityConfig.class})
@WebMvcTest(RecordController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class RecordControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private Gson gson;

    @MockBean
    private RecordService service;

    @MockBean
    private RecordMapper mapper;

    private final static String RECORD_DEFAULT_URL = "/records";

    @Value("${spring.servlet.multipart.location}")
    private String location;


    @Test
    @DisplayName("여행 일지를 등록한다.")
    void postRecordTest() throws Exception{
        
        //given
        RecordDto.Post request = (RecordDto.Post)StubData.MockRecord.getRequestBody(HttpMethod.POST);
        String jsonData = gson.toJson(request);


        given(mapper.recordPostToRecord(Mockito.any(RecordDto.Post.class))).willReturn(Record.builder().build());
        given(service.createRecord(Mockito.any(Record.class))).willReturn(Record.builder().recordId(1L).build());
        
        //when
        ResultActions actions =
            mockMvc.perform(
                    post(RECORD_DEFAULT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData)
                );
                
        //then
        actions
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", is(startsWith("/records/"))))
            .andDo(
                MockMvcRestDocumentationWrapper.document("일지 등록",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(
                        ResourceSnippetParameters.builder()
                            .description("일지 등록")
                            .requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                            )
                            .responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소드의 URI")
                            )
                            .build()
                    )
                )
            );
        
    
    }
    
    @Test
    @DisplayName("여행 일지를 조회한다.")
    void getRecordTest() throws Exception{
        //given
        RecordDto.Response response = (RecordDto.Response)StubData.MockRecord.getRequestBody(HttpMethod.GET);
        String jsonData = gson.toJson(response);

        given(service.findRecord(Mockito.anyLong())).willReturn(Record.builder().build());
        given(mapper.recordToRecordResponse(Mockito.any(Record.class))).willReturn(response);

        //when
        ResultActions actions =
            mockMvc.perform(
                get(RECORD_DEFAULT_URL+"/{record-id}",response.getRecordId())
                    .contentType(MediaType.APPLICATION_JSON)
            );

        //then
        actions
            .andExpect(status().isOk())
            .andDo(
                MockMvcRestDocumentationWrapper.document("일지 조회",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        List.of(parameterWithName("record-id").description("일지 식별자 ID"))
                    ),
                    resource(
                        ResourceSnippetParameters.builder()
                            .description("일지 조회")
                            .responseFields(
                                List.of(
                                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터").optional(),
                                    fieldWithPath("data.recordId").type(JsonFieldType.NUMBER).description("일지 식별자"),
                                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                    fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성 날짜"),
                                    fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 날짜")
                                )
                            )
                            .build()
                    )
                )
            );

    }

    @Test
    @DisplayName("사진들을 등록한다.")
    void uploadRecordImgTest() throws Exception{
        //given
        Long recordId = 1L;

        byte[] image1Content = "image1".getBytes();
        MockMultipartFile image1 = new MockMultipartFile(
            "images", "image1.jpg", MediaType.IMAGE_JPEG_VALUE, image1Content);

        byte[] image2Content = "image2".getBytes();
        MockMultipartFile image2 = new MockMultipartFile(
            "images", "image2.jpg", MediaType.IMAGE_JPEG_VALUE, image2Content);


        try (MockedStatic<ImageManager> mockedStatic = mockStatic(ImageManager.class)) {
            mockedStatic.when(() -> ImageManager.uploadImages(anyList(), anyString())).thenReturn(true);

            //when
            ResultActions actions = mockMvc.perform(
                multipart(RECORD_DEFAULT_URL + "/{record-id}/img", recordId)
                    .file(image1)
                    .file(image2)
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)

            );

            //then
            actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/records/"))))
                .andDo(
                    MockMvcRestDocumentationWrapper.document("사진 등록",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                            List.of(parameterWithName("record-id").description("일지 식별자 ID"))
                        ),
                        resource(
                            ResourceSnippetParameters.builder()
                                .description("사진 등록")
                                .responseHeaders(
                                    headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소스의 URI")
                                )
                                .build()
                        )
                    )
                );
        }

    }


    @Test
    @DisplayName("등록한 사진을 조회한다.")
    void getRecordImgTest() throws Exception{
        //given
        Long recordId = 1L;
        Long userId = 1L;

        String dirName = location + "/" + userId + "/" + recordId;

        List<Resource> imageFiles = new ArrayList<>();
        Resource imageFile1 = new UrlResource(new File(dirName + "/image1.jpg").toURI());
        Resource imageFile2 = new UrlResource(new File(dirName + "/image2.jpg").toURI());
        imageFiles.add(imageFile1);
        imageFiles.add(imageFile2);


        try (MockedStatic<ImageManager> mockedStatic = mockStatic(ImageManager.class)) {
            mockedStatic.when(() -> ImageManager.loadImages(anyString())).thenReturn(imageFiles);

            //when
            ResultActions actions = mockMvc.perform(
                get(RECORD_DEFAULT_URL + "/{record-id}/img", recordId)
                    .contentType(MediaType.APPLICATION_JSON)

            );

            //then
            actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.images", hasSize(2)))
                .andExpect(jsonPath("$.images[0]", is(notNullValue())))
                .andExpect(jsonPath("$.images[1]", is(notNullValue())))
                .andDo(
                    MockMvcRestDocumentationWrapper.document("사진 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                            List.of(parameterWithName("record-id").description("일지 식별자 ID"))
                        ),
                        resource(
                            ResourceSnippetParameters.builder()
                                .description("사진 조회")
                                .responseFields(
                                    fieldWithPath("images").description("사진 목록")

                                )
                                .build()
                        )
                    )
                );
        }

    }




    
    
    


    
    
    
    
    

}
