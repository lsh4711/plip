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
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Aspect
@Component
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
                // objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                // objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
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
            logger.error("Request context is not available");
        }

    }
}
