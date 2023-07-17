package com.server.token.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;

import com.server.global.auth.jwt.JwtTokenizer;
import com.server.global.auth.utils.AccessTokenRenewalUtil;
import com.server.global.auth.utils.Token;
import com.server.helper.StubData;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TokenControllerTest {
    private final String TOKEN_DEFULT_URI = "/api/tokens";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @MockBean
    AccessTokenRenewalUtil accessTokenRenewalUtil;

    @Test
    @DisplayName("리프레쉬 토큰으로 엑세스 토큰을 재발급합니다.")
    void getToken() throws Exception {
        //given
        String refreshToken = StubData.MockSecurity.getValidRefreshToken(jwtTokenizer.getSecretKey());
        String accessToken = StubData.MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey());
        Token token = Token.builder()
            .refreshToken(refreshToken)
            .refreshToken(accessToken)
            .build();

        given(accessTokenRenewalUtil.renewAccessToken(Mockito.any(HttpServletRequest.class))).willReturn(token);
        //when
        ResultActions actions = mockMvc.perform(
                get(TOKEN_DEFULT_URI)
                    .cookie(new Cookie("Refresh", refreshToken))
                    .contentType(MediaType.APPLICATION_JSON))
            //then
            .andExpect(status().isOk())
            .andDo(
                MockMvcRestDocumentationWrapper.document("회원 등록 예제",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("Token")
                            .description("회원 등록")
                            .responseHeaders(
                                headerWithName("Authorization").description("재발급받은 인증 토큰")
                            )
                            .build())));

    }
}
