package com.server.domain.mail.service;

import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.server.domain.mail.entity.AuthMailCode;
import com.server.domain.member.entity.Member;
import com.server.domain.member.repository.MemberRepository;
import com.server.domain.schedule.entity.Schedule;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.MailUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final AuthMailCodeService authMailCodeService;
    private final MemberRepository memberRepository;
    private final MailUtils mailUtils;

    //이메일 전송
    @Async
    public void sendMail(String email, String type) {
        String authCode = mailUtils.createCode();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText(setContext(authCode, type), true);
            if (type.equals("signup") || type.equals("pw")) {
                //이메일 인증 코드를 update
                authMailCodeService.saveOrUpdateAuthCode(authCode, email);
            }
            //이메일 제목
            mimeMessageHelper.setSubject(mailUtils.setSubject(type));
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            log.info("##" + email + " 메일 보내기 실패!! 에러 메시지: " + e);
        }
    }

    //이메일 인증
    public void verificationEmail(String email, String type) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        //비밀번호를 찾을 때 이메일 인증을 하는 경우
        if (type.equals("pw") && findMember.isEmpty())
            throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
            //회원 가입에서 이메일 인증을 하는 경우
        else if (type.equals("signup") && findMember.isPresent())
            throw new CustomException(ExceptionCode.EMAIL_EXISTS);
    }

    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }

    //이메일 인증 코드 인증
    public void authenticationMailCode(AuthMailCode userAuthMailCode) {
        AuthMailCode findAuthCode = authMailCodeService.getAuthMailCodeByEmail(userAuthMailCode.getEmail());
        if (!findAuthCode.getAuthCode().equals(userAuthMailCode.getAuthCode())) {
            log.error("### 사용자의 인증 코드와 실제 인증 코드가 일치하지 않습니다.");
            throw new CustomException(ExceptionCode.AUTH_MAIL_CODE_MISMATCH);
        }
        //인증이 끝나면 인증 코드는 삭제
        authMailCodeService.removeAuthCode(findAuthCode.getEmail());
    }

    //일정에 관한 이메일 전송
    @Async
    public void sendScheduleMail(Schedule schedule, Member member) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            mimeMessageHelper.setTo(member.getEmail());
            Context context = new Context();
            context.setVariable("content", mailUtils.getContent(schedule, member));
            context.setVariable("uri", mailUtils.getUri(schedule, member));
            mimeMessageHelper.setText(templateEngine.process("schedule", context), true);
            mimeMessageHelper.setSubject(mailUtils.getMailTitle(schedule, member));
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            log.info("##" + member.getEmail() + " 메일 보내기 실패!! 에러 메시지: " + e);
        }
    }
}
