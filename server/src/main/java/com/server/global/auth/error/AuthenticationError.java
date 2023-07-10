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
        response.setStatus(getErrorStatus(exception).value());
        response.getWriter().write(gson.toJson(getErrorMessage(exception)));
    }
    public static HttpStatus getErrorStatus(Exception exception){
        if(exception.getClass().equals(CustomException.class))
            return ((CustomException)exception).getStatus();
        else
            return HttpStatus.UNAUTHORIZED;
    }
    public static String getErrorMessage(Exception exception){
        if(exception.getClass().equals(CustomException.class)){
            return exception.getMessage();
        }
        else{
            return "사용할 수 없는 사용자입니다.";
        }
    }
}
