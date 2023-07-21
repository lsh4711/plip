package com.server.global.exception;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity customExceptionHandler(CustomException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity dateTimeParseExceptionHandler(DateTimeParseException e) {
        return ResponseEntity.badRequest().body("날짜 형식이 올바르지 않습니다.");
    }

    @ExceptionHandler
    public ResponseEntity handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        Map<String, String> errorResponses = new HashMap<>();
        List<FieldError> errors = e.getBindingResult().getFieldErrors();

        //stream 사용하지 않아도 되서 수정
        errors.forEach(error -> errorResponses.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errorResponses);
    }

    // @ExceptionHandler
    public ResponseEntity exceptionHandler(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
