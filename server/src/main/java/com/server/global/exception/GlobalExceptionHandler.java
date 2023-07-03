package com.server.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity customExceptionHandler(CustomException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}
