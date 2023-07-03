package com.server.mail.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.server.domain.mail.controller.MailController;
import com.server.domain.mail.dto.MailDto;
import com.server.domain.mail.service.MailService;
import com.server.global.config.SecurityConfig;

@Import({SecurityConfig.class})
@WebMvcTest(MailController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class MailControllerTest {
    private final String MAIL_DEFULT_URI = "/api/mail";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private MailService service;

    @Test
    @DisplayName("이메일 인증을 한다.")
    void authEmail() throws Exception {
        //given
        String authCode = "abcd1234";
        MailDto.Post request = MailDto.Post.builder()
            .email("test@naver.com")
            .build();
        String jsonData = gson.toJson(request);

        given(service.sendMail(Mockito.anyString())).willReturn(authCode);
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
                .andExpect(jsonPath("$.authCode").value(authCode))
                .andDo(
                    MockMvcRestDocumentationWrapper.document("이메일 인증 예제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                .description("이메일 인증")
                                .requestFields(
                                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                                )
                                .responseFields(
                                    fieldWithPath("authCode").type(JsonFieldType.STRING).description("인증 코드")
                                )
                                .build()
                        )
                    )
                );
    }
}
