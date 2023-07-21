package com.server.domain.oauth.template;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.server.domain.member.entity.Member;
import com.server.domain.oauth.template.KakaoTemplateObject.Content;
import com.server.domain.oauth.template.KakaoTemplateObject.Feed;
import com.server.domain.oauth.template.KakaoTemplateObject.Link;
import com.server.domain.region.entity.Region;
import com.server.domain.schedule.entity.Schedule;

@Component
public class KakaoTemplateConstructor {
    private static final String[] REGION_LIST = { // 임시
        "busan", "chungbuk", "chungnam", "daegu", "daejeon",
        "gangwon", "gwangju", "gyeongbuk", "gyeonggi", "gyeongnam",
        "incheon", "jeju", "jeonbuk", "jeonnam", "seoul",
        "ulsan"
    };

    public Feed getWelcomeTemplate(Member member) {
        // Member
        long memberId = member.getMemberId();
        String email = member.getEmail();
        String nickname = member.getNickname();

        // Feed
        // String basesUrl = "https://plip.netlify.app/login";
        // String token = "test";
        // String shareUrl = String.format("%s?token=",
        //     basesUrl,
        //     token);

        // test
        Random random = new Random();
        int idx = random.nextInt(16);
        String region = REGION_LIST[idx];

        String shareUrl = "https://plip.netlify.app/";
        Link link = Link.builder()
                .web_url(shareUrl)
                .mobile_web_url(shareUrl)
                .build();
        Content content = Content.builder()
                .title(String.format("%s님의 가입을 환영합니다!", nickname))
                // .description("PliP과 함께 여행 일정을 작성하러 가볼까요?\n*이 메시지는 타인에게 공유하지 마세요!*")
                .description("PliP과 함께 여행 일정을 작성하러 가볼까요?")
                .image_width(600)
                .image_height(400)
                .image_url("https://teamdev.shop:8000/files/images?region=" + region)
                .link(link)
                .build();
        Feed feed = Feed.builder()
                .object_type("feed")
                .content(content)
                .button_title("PilP 자동 로그인")
                .build();

        return feed;
    }

    public Feed getFeedTemplate(Schedule schedule, Member member) {
        // Member
        long memberId = member.getMemberId();
        String email = member.getEmail();
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

        // Feed
        String basesUrl = "https://plip.netlify.app/plan/detail";
        String shareUrl = String.format("%s/%d/share?id=%d&email=%s",
            basesUrl,
            scheduleId,
            memberId,
            email);
        Link link = Link.builder()
                .web_url(shareUrl)
                .mobile_web_url(shareUrl)
                .build();
        Content content = Content.builder()
                .title(String.format("%s님의 %s 여행 일정입니다.", nickname, korName))
                .description(String.format("기간: %s \n~ %s (%s)", startDate, endDate, term))
                .image_width(600)
                .image_height(400)
                .image_url("https://teamdev.shop:8000/files/images?region=" + engName)
                .link(link)
                .build();
        Feed feed = Feed.builder()
                .object_type("feed")
                .content(content)
                .build();

        return feed;
    }
}
