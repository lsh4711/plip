package com.server.domain.schedule.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.server.domain.member.entity.Member;
import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.region.entity.Region;
import com.server.domain.region.repository.RegionRepository;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.repository.ScheduleRepository;
import com.server.domain.test.auth.KakaoAuth;
import com.server.domain.test.dto.Body.Content;
import com.server.domain.test.dto.Body.Feed;
import com.server.domain.test.dto.Body.Link;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.CustomBeanUtils;
import com.server.global.utils.CustomUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    private final RegionRepository regionRepository;

    private final KakaoAuth kakaoAuth;

    public Schedule saveSchedule(Schedule schedule) {
        String title = schedule.getTitle();
        int memberCount = schedule.getMemberCount();

        Region region = schedule.getRegion();
        String engName = region.getEngName();
        Region foundRegion = regionRepository.findByEngName(engName);
        String korName = foundRegion.getKorName();

        schedule.setRegion(foundRegion);
        if (title == null) {
            schedule.setTitle(String.format("%s 여행 레츠고!", korName));
        }
        if (memberCount <= 0) {
            schedule.setMemberCount(1);
        }

        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Schedule schedule) {
        long scheduleId = schedule.getScheduleId();
        Schedule foundSchedule = findSchedule(scheduleId);

        Region region = schedule.getRegion();
        String engName = region.getEngName();
        Region foundRegion = regionRepository.findByEngName(engName);

        schedule.setRegion(foundRegion);
        CustomBeanUtils.copyNonNullProperties(schedule, foundSchedule);
        saveSchedule(foundSchedule);

        return foundSchedule;
    }

    public Schedule findSchedule(long scheduleId) {
        long memberId = CustomUtil.getAuthId();
        Schedule schedule = scheduleRepository
                .findByScheduleIdAndMember_MemberId(scheduleId, memberId);

        if (schedule == null) {
            throw new CustomException(
                ExceptionCode.SCHEDULE_NOT_FOUND);
        }

        return schedule;
    }

    public List<Schedule> findSchedules() {
        Sort sort = Sort.by("createdAt").descending();
        long memberId = CustomUtil.getAuthId();
        List<Schedule> schedules = scheduleRepository.findAllByMember_memberId(memberId, sort);

        return schedules;
    }

    public Schedule findSharedSchedule(long scheduleId, long memberId, String email) {
        Schedule schedule = scheduleRepository
                .findByScheduleIdAndMember_MemberIdAndMember_Email(scheduleId, memberId, email);

        if (schedule == null) {
            throw new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND);
        }

        return schedule;
    }

    public void deleteSchedule(long scheduleId) {
        long memberId = CustomUtil.getAuthId();

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

    public Feed getFeedTemplate(Schedule schedule) {
        // Member
        Member member = schedule.getMember();
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

    @Async
    public void sendKakaoMessage(Schedule schedule, Member member) {
        KakaoToken kakaoToken = member.getKakaoToken();

        if (kakaoToken == null) {
            return; // or throw CustomExcepion
        }

        String accessToken = kakaoToken.getAccessToken();
        Feed template = getFeedTemplate(schedule);

        kakaoAuth.sendMessage(template, accessToken);
    }
}
