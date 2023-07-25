package com.server.domain.schedule.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.server.domain.member.entity.Member;
import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.oauth.service.KakaoApiService;
import com.server.domain.oauth.template.KakaoTemplateConstructor;
import com.server.domain.oauth.template.KakaoTemplateObject.Feed;
import com.server.domain.push.entity.Push;
import com.server.domain.push.entity.PushMessage;
import com.server.domain.push.service.PushService;
import com.server.domain.region.entity.Region;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.repository.ScheduleRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.AuthUtil;
import com.server.global.utils.CustomBeanUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    private final KakaoApiService kakaoApiService;
    private final KakaoTemplateConstructor kakaoTemplateConstructor;

    private final PushService pushService;

    private final PasswordEncoder passwordEncoder;

    @Value("${share.key}")
    private String shareSecretKey;

    public Schedule saveSchedule(Schedule schedule) {
        schedule.setStartDate(schedule.getStartDate().plusDays(1)); // 핫픽스용
        schedule.setEndDate(schedule.getEndDate().plusDays(1)); // 핫픽스용
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Schedule schedule) {
        long scheduleId = schedule.getScheduleId();
        Schedule foundSchedule = findScheduleOfAuthMember(scheduleId);

        CustomBeanUtils.copyNonNullProperties(schedule, foundSchedule);

        return scheduleRepository.save(foundSchedule);
    }

    public Schedule findScheduleOfAuthMember(long scheduleId) {
        long memberId = AuthUtil.getMemberId();
        Schedule schedule = scheduleRepository
                .findByScheduleIdAndMember_MemberId(scheduleId, memberId);

        if (schedule == null) {
            throw new CustomException(
                ExceptionCode.SCHEDULE_NOT_FOUND);
        }

        return schedule;
    }

    public List<Schedule> findSchedulesOfAuthMember() {
        Sort sort = Sort.by("createdAt").descending();
        long memberId = AuthUtil.getMemberId();
        List<Schedule> schedules = scheduleRepository.findAllByMember_memberId(memberId, sort);

        return schedules;
    }

    public Schedule findSharedSchedule(long scheduleId, long memberId, String code) {
        Schedule schedule = scheduleRepository
                .findByScheduleIdAndMember_MemberId(scheduleId, memberId);

        if (schedule == null) {
            throw new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND);
        }

        Member member = schedule.getMember();
        String email = member.getEmail();
        String raw = String.format("%d/%s/%s",
            memberId,
            email,
            shareSecretKey);
        String encoded = "{bcrypt}$2a$10$" + code;

        if (!passwordEncoder.matches(raw, encoded)) {
            throw new CustomException(ExceptionCode.SHARE_CODE_INVALID);
        }

        return schedule;
    }

    public String createShareUrl(long scheduleId, Member member) {
        long memberId = member.getMemberId();
        String email = member.getEmail();

        verify(scheduleId, memberId);

        String raw = String.format("%d/%s/%s",
            memberId,
            email,
            shareSecretKey);
        String code = passwordEncoder.encode(raw)
                .replace("{bcrypt}$2a$10$", "");

        String shareUrl = String.format("https://plip.netlify.app/plan/detail/%d/share?id=%d&code=%s",
            scheduleId,
            memberId,
            code);

        return shareUrl;
    }

    public void deleteSchedule(long scheduleId) {
        long memberId = AuthUtil.getMemberId();

        verify(scheduleId, memberId);
        scheduleRepository.deleteById(scheduleId);
    }

    public void verify(long scheduleId, long memberId) {
        boolean exists = scheduleRepository
                .existsByScheduleIdAndMember_MemberId(scheduleId, memberId);

        if (!exists) {
            throw new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND);
        }

    }

    @Async
    public void sendKakaoMessage(Schedule schedule) {
        Member member = schedule.getMember();
        KakaoToken kakaoToken = member.getKakaoToken();

        if (kakaoToken == null) {
            return; // or throw CustomExcepion
        }

        String accessToken = kakaoToken.getAccessToken();
        Feed feedTemplate = kakaoTemplateConstructor
                .getPostTemplate(schedule, member);

        kakaoApiService.sendMessage(feedTemplate, accessToken);
    }

    @Async
    public void sendPushMessage(Schedule schedule) {
        // Member
        Member member = schedule.getMember();
        String nickname = member.getNickname();
        Push push = member.getPush();

        if (push == null) {
            return;
        }

        // Schedule
        long scheduleId = schedule.getScheduleId();
        Region region = schedule.getRegion();
        String engName = region.getEngName();
        String korName = region.getKorName();
        LocalDate startDate = schedule.getStartDate();
        LocalDate endDate = schedule.getEndDate();
        int period = schedule.getPeriod();
        String term = period == 1 ? "당일치기" : String.format("%d박 %d일", period - 1, period);

        // PushMessage
        String token = push.getPushToken();
        String title = String.format("%s님의 %s 여행 일정입니다.", nickname, korName);
        String body = String.format("기간: %s \n~ %s (%s)", startDate, endDate, term);
        String shareUrl = createShareUrl(scheduleId, member);

        PushMessage pushMessage = PushMessage.builder()
                .token(token)
                .title(title)
                .body(body)
                .region(engName)
                // .imageUrl(null)
                .url(shareUrl)
                .build();

        pushService.sendPush(pushMessage);
    }
}
