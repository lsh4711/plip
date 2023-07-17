package com.server.global.log;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class RequestLoggingAspect {
    private static final Logger logger = LogManager.getLogger(RequestLoggingAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerClassMethods() {}

    @Before("restControllerClassMethods()")
    public void logRequestBody(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // URL
        String url = request.getRequestURL().toString();

        // Header
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }

        Object[] args = joinPoint.getArgs();

        if (args.length > 0) {
            Object requestBody = args[0];
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String requestBodyJson = objectMapper.writeValueAsString(requestBody);
                logger.debug("URL: {}", url);
                logger.debug("Headers: {}", headers);
                logger.debug("Request body: {}", requestBodyJson);
            } catch (JsonProcessingException e) {
                logger.error("Failed to convert request body to JSON", e);
            }
        }
    }
}
