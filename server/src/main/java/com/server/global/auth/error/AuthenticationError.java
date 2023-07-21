package com.server.global.auth.error;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.google.gson.Gson;
import com.server.global.exception.CustomException;

public class AuthenticationError {

	public static void sendErrorResponse(HttpServletResponse response, Exception exception) throws IOException {
		Gson gson = new Gson();
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(getErrorStatus(exception).value());
		response.getWriter().write(gson.toJson(getErrorMessage(exception)));
	}

	public static HttpStatus getErrorStatus(Exception exception) {
		if (exception.getClass().equals(CustomException.class))
			return ((CustomException)exception).getStatus();
		else
			return HttpStatus.UNAUTHORIZED;
	}

	public static String getErrorMessage(Exception exception) {
		if (exception.getClass().equals(CustomException.class)) {
			return exception.getMessage();
		} else {
			return "권한이 없는 사용자입니다.";
		}
	}

}
