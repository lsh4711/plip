package com.server.global.auth.handler.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.server.domain.member.entity.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {
        Member member = (Member)authentication.getPrincipal();
        log.info("### Authenticated successfully!");
        log.info("### 로그인 정보 이메일: " + member.getEmail());
    }
}
