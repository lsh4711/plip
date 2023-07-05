package com.server.member.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.BDDMockito.*;
<<<<<<< HEAD
=======
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
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
<<<<<<< HEAD
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
=======
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
<<<<<<< HEAD
import com.server.domain.member.controller.MemberController;
import com.server.domain.member.dto.MemberDto;
import com.server.domain.member.entity.Member;
import com.server.domain.member.mapper.MemberMapper;
import com.server.domain.member.service.MemberService;
import com.server.global.config.SecurityConfig;
import com.server.helper.StubData;

@Import({SecurityConfig.class})
@WebMvcTest(MemberController.class)
=======
import com.server.domain.member.dto.MemberDto;
import com.server.domain.member.entity.Member;
import com.server.domain.member.mapper.MemberMapper;
import com.server.domain.member.repository.MemberRepository;
import com.server.domain.member.service.MemberService;
import com.server.global.auth.dto.LoginDto;
import com.server.helper.StubData;

@SpringBootTest
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class MemberControllerTest {
    private final String MEMBER_DEFULT_URI = "/api/users";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private MemberService service;
    @MockBean
    private MemberMapper mapper;
<<<<<<< HEAD
=======
    @Autowired
    private MemberRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523

    @Test
    @DisplayName("회원 가입을 한다.")
    void signupMember() throws Exception {
        //given
<<<<<<< HEAD
        MemberDto.Post request = (MemberDto.Post)StubData.MockMember.getRequestBody(HttpMethod.POST);
=======
        MemberDto.Post request = (MemberDto.Post)StubData.MockMember.getRequestBody("memberPost");
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
        String jsonData = gson.toJson(request);

        given(mapper.memberDtoPostToMember(Mockito.any(MemberDto.Post.class))).willReturn(Member.builder().build());
        given(service.createMember(Mockito.any(Member.class))).willReturn(Member.builder().memberId(1L).build());
        //when
        ResultActions actions =
            mockMvc.perform(
                    post(MEMBER_DEFULT_URI + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData)
                )
                //then
                .andExpect(status().isCreated())
                .andDo(
                    MockMvcRestDocumentationWrapper.document("회원 등록 예제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                .description("회원 등록")
                                .requestFields(
                                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀 번호"),
                                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                                )
                                .responseFields()
                                .build()
                        )
                    )
                );
    }

<<<<<<< HEAD
=======
    @Test
    @DisplayName("로그인을 한다.")
    void loginMember() throws Exception {
        //given
        LoginDto request = (LoginDto)StubData.MockMember.getRequestBody("loginDto");
        String jsonData = gson.toJson(request);

        repository.save(
            Member.builder().memberId(1L).email("test123@naver.com").password(passwordEncoder.encode("q12345678@"))
                .nickname("test").build());
        //when
        ResultActions actions =
            mockMvc.perform(
                    post(MEMBER_DEFULT_URI + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData)
                )
                //then
                .andExpect(status().isOk())
                .andDo(
                    MockMvcRestDocumentationWrapper.document("회원 로그인 예제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                .description("회원 로그인")
                                .responseHeaders(
                                    headerWithName("Authorization").description("발급받은 인증 토큰"),
                                    headerWithName("Refresh").description("발급받은 리프레쉬 토큰")
                                )
                                .responseFields()
                                .build()
                        )
                    )
                );
    }

>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
}
