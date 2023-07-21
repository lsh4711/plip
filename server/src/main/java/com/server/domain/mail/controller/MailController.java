package com.server.domain.mail.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.mail.dto.AuthMailCodeDto;
import com.server.domain.mail.dto.MailDto;
import com.server.domain.mail.mapper.AuthMailCodeMapper;
import com.server.domain.mail.service.MailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/mail")
@RestController
public class MailController {
	private final MailService mailService;
	private final AuthMailCodeMapper authMailCodeMapper;

	//회원가입시 이메일 인증
	@PostMapping()
	public ResponseEntity<?> postSignupSendEmail(@RequestParam("type") String type,
		@Valid @RequestBody MailDto.Post request) {
		mailService.verificationEmail(request.getEmail(), type);
		mailService.sendMail(request.getEmail(), type);
		return ResponseEntity.ok().build();
	}

	//이메일 인증 여부 코드로 확인
	@PostMapping("/auth")
	public ResponseEntity<?> postAuthEmail(@RequestBody AuthMailCodeDto.Post request) {
		mailService.authenticationMailCode(authMailCodeMapper.authMailCodeDtoPostToAuthMailCode(request));
		return ResponseEntity.ok().build();
	}

}
