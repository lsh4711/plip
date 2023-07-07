package com.server.global.auth.handler;

import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.server.domain.member.entity.Member;
import com.server.domain.member.mapper.MemberMapper;
import com.server.global.auth.jwt.DelegateTokenUtil;
import com.server.global.auth.utils.OAuthAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final DelegateTokenUtil delegateTokenUtil;
    private final MemberMapper memberMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        Member oAuth2User = memberMapper.oauthAttributesToMember((OAuthAttributes)authentication.getPrincipal());
        redirect(request, response, oAuth2User);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, Member member) throws
        IOException {

        String accessToken = delegateTokenUtil.delegateAccessToken(member);
        String refreshToken = delegateTokenUtil.delegateRefreshToken(member);

        String uri = createURI(accessToken, refreshToken).toString();
        log.info("## OAuth2 로그인 성공! 토큰을 발급합니다. 해당 주소로 보낼게용 " + uri);
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);
        return UriComponentsBuilder
            .newInstance()
            .scheme("http")
            .host("localhost") // 리다이렉트 시킬 클라이언트 주소
            .port(8080)
            .path("/oauth")
            .queryParams(queryParams)
            .build()
            .toUri();
    }
}
