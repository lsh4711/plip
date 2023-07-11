package com.server.domain.mail.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.domain.mail.entity.AuthMailCode;
import com.server.domain.mail.repository.AuthMailCodeRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthMailCodeService {
    private final AuthMailCodeRepository authMailCodeRepository;

    public void saveAuthCode(String authCode, String email) {
        authMailCodeRepository.save(AuthMailCode.builder().authCode(authCode).email(email).build());
    }

    @Transactional(readOnly = true)
    public AuthMailCode getAuthMailCodeByEmail(String email) {
        AuthMailCode authMailCode = authMailCodeRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ExceptionCode.AUTH_MAIL_CODE_NOT_FOUND));
        return authMailCode;
    }

    public void removeAuthCode(String email) {
        authMailCodeRepository.findByEmail(email)
            .ifPresent(authMailCodeRepository::delete);
    }
}
