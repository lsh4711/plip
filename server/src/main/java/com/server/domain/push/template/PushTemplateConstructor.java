package com.server.domain.push.template;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.server.domain.member.entity.Member;
import com.server.domain.push.entity.Push;
import com.server.domain.region.entity.Region;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.service.ScheduleService;

@Component
public class PushTemplateConstructor {
    @Lazy
    @Autowired
    private ScheduleService scheduleService;

    @Value("${share.key}")
    private String shareSecretKey;

    private String baseUrl = "https://plip.netlify.app/";

    public PushTemplate getWelcomeTemplate(String token, String nickname) {
        PushTemplate pushTemplate = PushTemplate.builder()
                .token(token)
                .title(String.format("%s님의 가입을 환영합니다!", nickname))
                .body("PliP과 함께 여행 일정을 작성하러 가볼까요?")
                .build();

        return pushTemplate;
    }

    public PushTemplate getPostScheduleTemplate(Schedule schedule,
            Member member,
            Push push) {
        // Member member
        String nickname = member.getNickname();

        // Schedule
        long scheduleId = schedule.getScheduleId();
        Region region = schedule.getRegion();
        String engName = region.getEngName();
        String korName = region.getKorName();
        LocalDate startDate = schedule.getStartDate();
        LocalDate endDate = schedule.getEndDate();
        int period = schedule.getPeriod();
        String term = period == 1 ? "당일치기" : String.format("%d박 %d일", period - 1, period);

        // PushTemplate
        String shareUrl = scheduleService.createShareUrl(scheduleId, member);
        PushTemplate pushTemplate = PushTemplate.builder()
                .token(push.getPushToken())
                .title(String.format("%s님의 %s 여행 일정입니다.", nickname, korName))
                .body(String.format("기간: %s \n~ %s (%s)", startDate, endDate, term))
                .region(engName)
                // .imageUrl(null)
                .url(shareUrl)
                .build();

        return pushTemplate;
    }

    public PushTemplate getDeleteScheduleTemplate(Schedule schedule) {

        return null;
    }
}
