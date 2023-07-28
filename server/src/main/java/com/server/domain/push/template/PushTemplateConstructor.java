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
import com.server.global.utils.CustomRandom;

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
                .body(String.format("기간: %s ~ %s (%s)", startDate, endDate, term))
                .imageUrl(CustomRandom.getCustomRegionUrl(engName))
                .url(shareUrl)
                .build();

        return pushTemplate;
    }

    public PushTemplate getDeleteScheduleTemplate(Schedule schedule) {

        return null;
    }

    // 이벤트용
    public PushTemplate getEventTemplate(String token, String nickname, long giftId) {
        PushTemplate pushTemplate = PushTemplate.builder()
                .token(token)
                .title(String.format("%s님 사탕이 도착했어요~", nickname))
                .body(String.format("선착순 이벤트에 %d등으로 참여하셨습니다.", giftId))
                .imageUrl("https://teamdev.shop/files/images/gifts?id=" + giftId)
                .build();

        return pushTemplate;
    }

    // 이벤트용
    public PushTemplate getNoticeTemplate(String token, String nickname, String title, String message) {
        PushTemplate pushTemplate = PushTemplate.builder()
                .token(token)
                .title(title)
                .body(String.format("%s님 %s", nickname, message))
                .imageUrl("https://teamdev.shop/files/images/gifts?id=999")
                .url("https://teamdev.shop/events")
                .build();

        return pushTemplate;
    }
}
