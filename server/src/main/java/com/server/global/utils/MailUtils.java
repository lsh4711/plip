package com.server.global.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.server.domain.member.entity.Member;
import com.server.domain.schedule.entity.Schedule;

@Component
public class MailUtils {

    public String setSubject(String type) {
        if (type.equals("welcome"))
            return "[PliP] 회원가입을 축하드립니다!";
        else
            return "[PliP] 이메일 인증을 위한 인증코드를 발송했습니다.";
    }

    public String createCode() {
        Random random = new Random();
        //TODO: StringBuffer를 사용하신 이유와 인증 코드
        StringBuffer key = new StringBuffer();

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

    public String getMailTitle(Schedule schedule, Member member){
        int period = schedule.getPeriod();
        String term = period == 1 ? "당일치기" : String.format("%d박 %d일", period - 1, period);
        return String.format("[PliP] %s 님의 %s %s 여행 일정이 도착했습니다.", member.getNickname(), schedule.getRegion().getKorName(), term);
    }

    public String getContent(Schedule schedule, Member member){
        int period = schedule.getPeriod();
        String term = period == 1 ? "당일치기" : String.format("%d박 %d일", period - 1, period);
        return String.format("%s 님! %s 로 %s 여행을 계획하셨습니다!", member.getNickname(), schedule.getRegion().getKorName(), term);
    }

    public String getUri(Schedule schedule, Member member){
        return String.format(
            "https://plip.netlify.app/plan/detail/%d/share?id=%d&email=%s", schedule.getScheduleId(),
            member.getMemberId(), member.getEmail());
    }
}
