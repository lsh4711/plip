package com.server.global.exception;

import java.time.format.DateTimeParseException;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.server.global.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity customExceptionHandler(CustomException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity dateTimeParseExceptionHandler(DateTimeParseException e) {
        return new ResponseEntity<>("날짜 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        //final ErrorResponse response = ErrorResponse.of(e.getBindingResult());
        return new ResponseEntity<>(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    // @ExceptionHandler
    public ResponseEntity exceptionHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
