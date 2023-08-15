package com.server.global.log;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class RequestLoggingAspect {
    private final Logger logger = LogManager.getLogger(RequestLoggingAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerClassMethods() {
    }

    @Before("restControllerClassMethods()")
    public void logRequestBody(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            // Method
            String method = request.getMethod();

            // URL
            String url = request.getRequestURL().toString();

            // Headers
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
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                try {
                    // Request body
                    String requestBodyJson = objectMapper.writeValueAsString(requestBody);
                    String content = new StringBuilder()
                            .append("URL: [{}]{}\n")
                            .append("- Headers: {}\n")
                            .append("- Request body: {}")
                            .toString();
                    logger.info(content,
                        method, url,
                        headers,
                        requestBodyJson);
                } catch (JsonProcessingException e) {
                    logger.error("Failed to convert request body to JSON", e);
                }
            }
        } else {
            log.error("Request context is not available");
        }

    }
}
