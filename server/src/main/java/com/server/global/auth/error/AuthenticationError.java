package com.server.global.auth.error;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.server.global.exception.CustomException;

import io.jsonwebtoken.ExpiredJwtException;

public class AuthenticationError {
    public static void sendErrorResponse(HttpServletResponse response, Exception exception) throws IOException {
        Gson gson = new Gson();
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(gson.toJson(getErrorMessage(exception)));
    }

    public static String getErrorMessage(Exception exception){
        if(exception.getClass().equals(InternalAuthenticationServiceException.class)){
            return "회원가입되지 않은 이메일입니다.";
        }else if(exception.getClass().equals(BadCredentialsException.class)){
            return "비밀번호가 맞지 않습니다.";
        }else if(exception.getClass().equals(ExpiredJwtException.class)){
            return "토큰이 만료되어 재발급됐습니다.";
        }else if(exception.getClass().equals(CustomException.class)){
            return exception.getMessage();
        }
        else{
            return "사용할 수 없는 사용자입니다.";
        }
    }
}
