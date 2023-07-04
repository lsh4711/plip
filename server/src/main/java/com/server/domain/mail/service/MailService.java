package com.server.domain.mail.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.server.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final MemberService memberService;
    private final SpringTemplateEngine templateEngine;

    public String sendMail(String email) {
        String authCode = createCode();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email); // 메일 수신자
            mimeMessageHelper.setSubject("[PLIP] 이메일 인증을 위한 인증코드를 발송했습니다."); // 메일 제목
            mimeMessageHelper.setText(setContext(authCode), true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);
            return authCode;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
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

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

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
}
