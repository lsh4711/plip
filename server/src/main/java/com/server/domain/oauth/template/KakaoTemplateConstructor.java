package com.server.domain.oauth.template;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.server.domain.member.entity.Member;
import com.server.domain.oauth.template.KakaoTemplateObject.Content;
import com.server.domain.oauth.template.KakaoTemplateObject.Feed;
import com.server.domain.oauth.template.KakaoTemplateObject.Link;
import com.server.domain.oauth.template.KakaoTemplateObject.Text;
import com.server.domain.region.entity.Region;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.service.ScheduleService;
import com.server.global.utils.CustomRandom;

@Component
public class KakaoTemplateConstructor {
    @Lazy
    @Autowired
    private ScheduleService scheduleService;

    @Value("${share.key}")
    private String shareSecretKey;

    private String baseUrl = "https://plip.netlify.app/";

    public Feed getWelcomeTemplate(Member member) {
        String nickname = member.getNickname();
        String region = CustomRandom.getRandomRegion();

        Link link = Link.builder()
                .web_url(baseUrl)
                .mobile_web_url(baseUrl)
                .build();

        Content content = Content.builder()
                .title(String.format("%s님의 가입을 환영합니다!", nickname))
                // .description("PliP과 함께 여행 일정을 작성하러 가볼까요?\n*이 메시지는 타인에게 공유하지 마세요!*")
                .description("PliP과 함께 여행 일정을 작성하러 가볼까요?")
                .image_width(600)
                .image_height(400)
                .image_url("https://teamdev.shop/files/images?region=" + region)
                .link(link)
                .build();

        Feed feed = Feed.builder()
                .object_type("feed")
                .content(content)
                .button_title("PilP으로 이동")
                .build();

        return feed;
    }

    public Feed getPostTemplate(Schedule schedule, Member member) {
        // Member
        String nickname = member.getNickname();

        // Schedule
        long scheduleId = schedule.getScheduleId();
        LocalDate startDate = schedule.getStartDate();
        LocalDate endDate = schedule.getEndDate();
        int period = schedule.getPeriod();
        String term = period == 1 ? "당일치기" : String.format("%d박 %d일", period - 1, period);

        // Region
        Region region = schedule.getRegion();
        String engName = region.getEngName();
        String korName = region.getKorName();

        // Link
        String shareUrl = scheduleService.createShareUrl(scheduleId, member);
        Link link = Link.builder()
                .web_url(shareUrl)
                .mobile_web_url(shareUrl)
                .build();

        Content content = Content.builder()
                .title(String.format("%s님의 %s 여행 일정입니다.", nickname, korName))
                .description(String.format("기간: %s \n~ %s (%s)", startDate, endDate, term))
                .image_width(600)
                .image_height(400)
                .image_url("https://teamdev.shop/files/images?region=" + engName)
                .link(link)
                .build();

        Feed feed = Feed.builder()
                .object_type("feed")
                .content(content)
                .build();

        return feed;
    }

    public Text getScheduledTemplate(Member member, Schedule schedule, int hour) {
        // Member
        String nickname = member.getNickname();

        // Schedule
        long scheduleId = schedule.getScheduleId();

        // Region
        Region region = schedule.getRegion();
        String korName = region.getKorName();
        String message;
        String button_title = null;

        String shareUrl = baseUrl;

        if (hour == 22) {
            message = String.format("%s님! %s 여행은 즐거우셨나요?!",
                nickname,
                korName);
            button_title = "일지 작성하러 가기";
        } else {
            String prefix = hour == 7 ? "오늘" : "내일";
            message = String.format("%s님! %s은 설레는 %s 여행날이에요!",
                nickname,
                prefix,
                korName);
            shareUrl = scheduleService.createShareUrl(scheduleId, member);
        }

        // Link
        Link link = Link.builder()
                .web_url(shareUrl)
                .mobile_web_url(shareUrl)
                .build();

        Text textTemplate = Text.builder()
                .object_type("text")
                .text(message)
                .button_title(button_title)
                .link(link)
                .build();

        return textTemplate;
    }
}
