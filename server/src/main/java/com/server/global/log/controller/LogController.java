package com.server.global.log.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.global.log.service.LogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logs")
public class LogController { // 나중에 쿼리스트링으로 끝 N줄만 출력
    private final LogService logService;

    @GetMapping("/requests")
    public ResponseEntity getRequestLog() {
        StringBuilder requestLog = logService.getLog("request");

        return ResponseEntity.ok(requestLog);
    }

    @GetMapping("/springs")
    public ResponseEntity getSpringLog() {
        StringBuilder springLog = logService.getLog("spring");

        return ResponseEntity.ok(springLog);
    }
}
