package com.server.member.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.server.domain.member.dto.MemberDto;
import com.server.domain.member.entity.Member;
import com.server.domain.member.mapper.MemberMapper;
import com.server.domain.member.repository.MemberRepository;
import com.server.domain.member.service.MemberService;
import com.server.global.auth.dto.LoginDto;
import com.server.global.auth.jwt.JwtTokenizer;
import com.server.helper.StubData;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    @Autowired
    private MemberRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    private String accessTokenForUser;

    @BeforeAll
    public void init() {
        accessTokenForUser = StubData.MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey(), "USER");
    }

    @Test
    @DisplayName("회원 가입을 한다.")
    void signupMember() throws Exception {
        //given
        MemberDto.Post request = (MemberDto.Post)StubData.MockMember.getRequestBody("memberPost");
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

    @Test
    @DisplayName("회원정보를 수정한다.")
    void patchMember() throws Exception {
        //given
        MemberDto.Patch request = (MemberDto.Patch)StubData.MockMember.getRequestBody("memberPatch");
        String jsonData = gson.toJson(request);

        given(mapper.memberDtoPatchToMember(Mockito.any(MemberDto.Patch.class))).willReturn(Member.builder().build());
        given(service.updateMember(Mockito.anyString(), Mockito.any(Member.class))).willReturn(
            Member.builder().memberId(1L).build());

        //when
        ResultActions actions =
            mockMvc.perform(
                    patch(MEMBER_DEFULT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                        .content(jsonData)
                )
                //then
                .andExpect(status().isCreated())
                .andDo(
                    MockMvcRestDocumentationWrapper.document("회원 수정 예제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                .description("회원 수정")
                                .requestHeaders(
                                    headerWithName("Authorization").description("발급받은 인증 토큰")
                                )
                                .build()
                        )
                    )
                );
    }

    @Test
    @DisplayName("로그인한 회원정보를 조회한다.")
    void getLoginMember() throws Exception {
        //given
        MemberDto.Response response = StubData.MockMember.getSingleResponseBody();

        given(service.findMemberByEmail(Mockito.anyString())).willReturn(Member.builder().memberId(1L).build());
        given(mapper.memberToMemberDtoResponse(Mockito.any(Member.class))).willReturn(response);

        //when
        ResultActions actions =
            mockMvc.perform(
                    get(MEMBER_DEFULT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                )
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nickname").value(response.getNickname()))
                .andDo(
                    MockMvcRestDocumentationWrapper.document("회원 조회 예제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                .description("회원 조회")
                                .requestHeaders(
                                    headerWithName("Authorization").description("발급받은 인증 토큰")
                                )
                                .responseFields(
                                    fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("회원 닉네임")
                                )
                                .build()
                        )
                    )
                );
    }

    @Test
    @DisplayName("회원을 탈퇴한다.")
    void deleteMember() throws Exception {
        //given
        doNothing().when(service).deleteMember(Mockito.anyString());
        //when
        ResultActions actions =
            mockMvc.perform(
                    delete(MEMBER_DEFULT_URI)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                //then
                .andExpect(status().isNoContent())
                .andDo(
                    MockMvcRestDocumentationWrapper.document("회원 삭제 예제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParameters.builder()
                                .description("회원 삭제")
                                .requestHeaders(
                                    headerWithName("Authorization").description("발급받은 인증 토큰")
                                )
                                .responseFields()
                                .build()
                        )
                    )
                );
    }

}
