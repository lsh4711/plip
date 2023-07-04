package com.server.domain.mail.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.mail.dto.MailDto;
import com.server.domain.mail.service.MailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/mail")
@RestController
public class MailController {

    private final MailService mailService;


    @PostMapping("signup")
    public ResponseEntity<?> postAuthEmail(@Valid @RequestBody MailDto.Post request) {
        String authCode = mailService.sendMail(request.getEmail());
        return new ResponseEntity<>(new MailDto.Response(authCode), HttpStatus.OK);
    }
}
