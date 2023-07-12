package com.server.member.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.server.domain.member.dto.MemberDto;
import com.server.domain.member.entity.Member;
import com.server.domain.member.entity.Member.Role;
import com.server.domain.member.mapper.MemberMapper;
import com.server.domain.member.repository.MemberRepository;
import com.server.domain.member.service.MemberService;
import com.server.global.auth.dto.LoginDto;
import com.server.global.auth.jwt.JwtTokenizer;
import com.server.helper.StubData;

@ActiveProfiles("test")
@SpringBootTest
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
    @Autowired
    private MemberRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    private String accessTokenForUser;

    @BeforeEach
    public void init() {
        accessTokenForUser = StubData.MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey());
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
        ResultActions actions = mockMvc.perform(
                post(MEMBER_DEFULT_URI + "/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonData))
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
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"))
                            .responseFields()
                            .build())));
    }

    @Test
    @DisplayName("로그인을 한다.")
    void loginMember() throws Exception {
        //given
        LoginDto request = (LoginDto)StubData.MockMember.getRequestBody("loginDto");
        String jsonData = gson.toJson(request);

        Member member = Member.builder()
            .memberId(1L)
            .email("admin")
            .password(passwordEncoder.encode("admin"))
            .nickname("관리자")
            .role(Role.USER)
            .build();

        repository.save(member);
        //when
        ResultActions actions = mockMvc.perform(
                post(MEMBER_DEFULT_URI + "/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonData))
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
                                headerWithName("Authorization").description("발급받은 인증 토큰"))
                            .responseFields()
                            .build())));
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
        ResultActions actions = mockMvc.perform(
                patch(MEMBER_DEFULT_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                    .content(jsonData))
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
                                headerWithName("Authorization").description("발급받은 인증 토큰"))
                            .requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("수정할 닉네임"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("수정할 비밀 번호")
                            )
                            .build())));
    }

    @Test
    @DisplayName("로그인한 회원정보를 조회한다.")
    void getLoginMember() throws Exception {
        //given
        MemberDto.Response response = StubData.MockMember.getSingleResponseBody();

        given(service.findMemberByEmail(Mockito.anyString())).willReturn(Member.builder().memberId(1L).build());
        given(mapper.memberToMemberDtoResponse(Mockito.any(Member.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                get(MEMBER_DEFULT_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser))
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
                                headerWithName("Authorization").description("발급받은 인증 토큰"))
                            .responseFields(
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("회원 닉네임"))
                            .build())));
    }

    @Test
    @DisplayName("회원을 탈퇴한다.")
    void deleteMember() throws Exception {
        //given
        doNothing().when(service).deleteMember(Mockito.anyString());
        //when
        ResultActions actions = mockMvc.perform(
                delete(MEMBER_DEFULT_URI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser)
                    .contentType(MediaType.APPLICATION_JSON))
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
                                headerWithName("Authorization").description("발급받은 인증 토큰"))
                            .responseFields()
                            .build())));
    }

    @Test
    @DisplayName("로그아웃을 한다.")
    void postLogoutMember() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(
                get(MEMBER_DEFULT_URI + "/logout")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenForUser))
            //then
            .andExpect(status().isOk())
            .andDo(
                MockMvcRestDocumentationWrapper.document("회원 로그아웃 예제",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(
                        ResourceSnippetParameters.builder()
                            .description("회원 로그아웃")
                            .requestHeaders(
                                headerWithName("Authorization").description("발급받은 인증 토큰")
                            )
                            .build())));
    }

    @Test
    @DisplayName("비밀번호를 재설정한다.")
    void patchPasswordMember() throws Exception {
        //given
        MemberDto.PasswordPatch request = (MemberDto.PasswordPatch)StubData.MockMember.getRequestBody("memberPwPatch");
        String jsonData = gson.toJson(request);

        given(mapper.memberDtoPasswordPatchToMember(Mockito.any(MemberDto.PasswordPatch.class))).willReturn(
            Member.builder().build());
        given(service.updatePassword(Mockito.any(Member.class))).willReturn(
            Member.builder().memberId(1L).build());

        //when
        ResultActions actions = mockMvc.perform(
                patch(MEMBER_DEFULT_URI + "/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonData))
            //then
            .andExpect(status().isOk())
            .andDo(
                MockMvcRestDocumentationWrapper.document("비밀번호 재설정 예제",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(
                        ResourceSnippetParameters.builder()
                            .description("비밀번호 재설정")
                            .requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("수정할 비밀 번호")
                            )
                            .build())));
    }

}
