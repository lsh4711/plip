package com.server.mail.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.server.domain.mail.controller.MailController;
import com.server.domain.mail.dto.AuthMailCodeDto;
import com.server.domain.mail.dto.MailDto;
import com.server.domain.mail.entity.AuthMailCode;
import com.server.domain.mail.mapper.AuthMailCodeMapper;
import com.server.domain.mail.service.MailService;

@WebMvcTest(MailController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
public class MailControllerTest {
    private final String MAIL_DEFULT_URI = "/api/mail";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private MailService service;
    @MockBean
    private AuthMailCodeMapper authMailCodeMapper;

    @Test
    @DisplayName("사용자에게 이메일로 인증코드를 보낸다.")
    void sendEmail() throws Exception {
        //given
        MailDto.Post request = MailDto.Post.builder()
            .email("test@naver.com")
            .build();
        String jsonData = gson.toJson(request);

        doNothing().when(service).sendMail(Mockito.anyString());
        //when
        ResultActions actions =
            mockMvc.perform(
                    post(MAIL_DEFULT_URI + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonData)
                )
                //then
                .andExpect(status().isOk())
                .andDo(
                    MockMvcRestDocumentationWrapper.document("이메일 전송 예제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                .description("이메일 전송")
                                .requestFields(
                                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                                )
                                .build()
                        )
                    )
                );
    }

    @Test
    @DisplayName("사용자에게 인증코드를 받아 이메일 인증을 한다.")
    void authEmail() throws Exception {
        //given
        String email = "test@naver.com";
        String authCode = "12345678";
        AuthMailCodeDto.Post request = AuthMailCodeDto.Post.builder()
            .email(email)
            .authCode(authCode)
            .build();
        String jsonData = gson.toJson(request);
        given(authMailCodeMapper.authMailCodeDtoPostToAuthMailCode(Mockito.any(AuthMailCodeDto.Post.class))).willReturn(
            AuthMailCode.builder().build());
        doNothing().when(service).authenticationMailCode(Mockito.any(AuthMailCode.class));
        //when
        ResultActions actions =
            mockMvc.perform(
                    post(MAIL_DEFULT_URI + "/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData)
                )
                //then
                .andExpect(status().isOk())
                .andDo(
                    MockMvcRestDocumentationWrapper.document("이메일 인증 예제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                .description("이메일 인증")
                                .requestFields(
                                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                    fieldWithPath("authCode").type(JsonFieldType.STRING).description("인증 코드")
                                )
                                .build()
                        )
                    )
                );
    }
}
