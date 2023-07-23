package com.server.global.auth.handler.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.server.global.auth.error.AuthenticationError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {
        log.error("### MemberAuthenticationEntryPoint Error!! : " + authException.getMessage());
        AuthenticationError.sendErrorResponse(response, authException);
    }
}
