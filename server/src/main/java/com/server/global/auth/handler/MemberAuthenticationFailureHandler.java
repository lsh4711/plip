package com.server.global.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.google.gson.Gson;
import com.server.global.exception.ExceptionCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
        log.error("### Authentication failed: {}", exception.getMessage());
        sendErrorResponse(response);
    }

    /**
     * TODO: 중복 코드 발생
     *       세세한 에러 핸들링 예정
     * */
    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(gson.toJson(ExceptionCode.UNAUTHORIZED.getMessage()));
    }
}
