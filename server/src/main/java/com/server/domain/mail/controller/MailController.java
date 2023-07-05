package com.server.domain.mail.controller;

import javax.validation.Valid;

<<<<<<< HEAD
import org.springframework.http.HttpStatus;
=======
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import com.server.domain.mail.dto.MailDto;
=======
import com.server.domain.mail.dto.AuthMailCodeDto;
import com.server.domain.mail.dto.MailDto;
import com.server.domain.mail.mapper.AuthMailCodeMapper;
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
import com.server.domain.mail.service.MailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/mail")
@RestController
public class MailController {
<<<<<<< HEAD
    private final MailService mailService;

    @PostMapping("signup")
    public ResponseEntity<?> postAuthEmail(@Valid @RequestBody MailDto.Post request) {
        String authCode = mailService.sendMail(request.getEmail());
        return new ResponseEntity<>(new MailDto.Response(authCode), HttpStatus.OK);
=======

    private final MailService mailService;
    private final AuthMailCodeMapper authMailCodeMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> postSendEmail(@Valid @RequestBody MailDto.Post request) {
        mailService.sendMail(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth")
    public ResponseEntity<?> postAuthEmail(@RequestBody AuthMailCodeDto.Post request) {
        mailService.authenticationMailCode(authMailCodeMapper.authMailCodeDtoPostToAuthMailCode(request));
        return ResponseEntity.ok().build();
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
    }
}
