package com.server.domain.mail.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
<<<<<<< HEAD
=======
import org.springframework.scheduling.annotation.Async;
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

<<<<<<< HEAD
import com.server.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

=======
import com.server.domain.mail.entity.AuthMailCode;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender javaMailSender;
<<<<<<< HEAD
    private final MemberService memberService;
    private final SpringTemplateEngine templateEngine;

    public String sendMail(String email) {
=======
    private final SpringTemplateEngine templateEngine;
    private final AuthMailCodeService authMailCodeService;

    /**
     * TODO: 메일이 있는지 검사를 해야할까?? 추후에 생각해보고 리팩토링
     * */
    @Async
    public void sendMail(String email) {
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
        String authCode = createCode();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
<<<<<<< HEAD
            mimeMessageHelper.setTo(email); // 메일 수신자
            mimeMessageHelper.setSubject("[PLIP] 이메일 인증을 위한 인증코드를 발송했습니다."); // 메일 제목
            mimeMessageHelper.setText(setContext(authCode), true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);
            return authCode;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
=======

            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("[PLIP] 이메일 인증을 위한 인증코드를 발송했습니다.");
            mimeMessageHelper.setText(setContext(authCode), true);
            javaMailSender.send(mimeMessage);
            authMailCodeService.saveAuthCode(authCode, email);
        } catch (MessagingException e) {
            log.info("##" + email + " 메일 보내기 실패!! 에러 메시지: " + e);
            throw new CustomException(ExceptionCode.MAIL_SEND_FAIL);
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
        }
    }

    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("email", context);
    }

    private String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

<<<<<<< HEAD
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

=======
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
            switch (index) {
                case 0:
                    key.append((char)((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char)((int)random.nextInt(26) + 65));
                    break;
                default:
                    key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }
<<<<<<< HEAD
=======

    public void authenticationMailCode(AuthMailCode userAuthMailCode) {
        AuthMailCode findAuthCode = authMailCodeService.getAuthMailCodeByEmail(userAuthMailCode.getEmail());
        if (!findAuthCode.getAuthCode().equals(userAuthMailCode.getAuthCode())) {
            log.error("### 사용자의 인증 코드와 실제 인증 코드가 일치하지 않습니다.");
            throw new CustomException(ExceptionCode.AUTH_MAIL_CODE_MISMATCH);
        }
    }
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
}
