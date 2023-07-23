package com.server.global.exception;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleCustomExceptionHandler(CustomException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    //Date 형식이 올바르지 않을 때
    @ExceptionHandler
    public ResponseEntity<String> handleDateTimeParseExceptionHandler(DateTimeParseException e) {
        return ResponseEntity.badRequest().body("날짜 형식이 올바르지 않습니다.");
    }

    //유효성 검사 실패 시
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        Map<String, String> errorResponses = new HashMap<>();
        List<FieldError> errors = e.getBindingResult().getFieldErrors();

        //stream 사용하지 않아도 되서 수정
        errors.forEach(error -> errorResponses.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errorResponses);
    }

    //잘못된 HTTP 요청이 들어왔을 경우
    @ExceptionHandler
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
    }

    //request body에 잘못된 데이터가 들어온 경우
    @ExceptionHandler
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    //메서드 파라미터, 리턴 값에 문제가 있을 경우
    @ExceptionHandler
    public ResponseEntity<Set<ConstraintViolation<?>>> handleConstraintViolationException(
        ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getConstraintViolations());
    }

    //RequestParam 이 누락되었을 경우
    @ExceptionHandler
    public ResponseEntity<String> handleMissingServletRequestParameterException(
        MissingServletRequestParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
