package com.server.global.init;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.category.entity.Category;
import com.server.domain.category.service.CategoryService;
import com.server.domain.member.entity.Member;
import com.server.domain.member.entity.Member.Role;
import com.server.domain.member.service.MemberService;
import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.oauth.service.KakaoTokenOauthService;
import com.server.domain.place.entity.Place;
import com.server.domain.place.service.PlaceService;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;
import com.server.domain.schedule.service.SchedulePlaceService;
import com.server.domain.schedule.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("default")
@RestController
@RequiredArgsConstructor
public class Init {
    // Member
    private final MemberService memberService;

    // Schedule
    private final ScheduleService scheduleService;

    // Place
    private final PlaceService placeService;

    // SchedulePlace
    private final SchedulePlaceService schedulePlaceService;

    // Category
    private final CategoryService categoryService;

    // KakaoToken
    private final KakaoTokenOauthService kakaoTokenOauthService;

    @PostConstruct
    public void init() {
        ArrayHashMap map = new ArrayHashMap();
        List<Category> categories = new ArrayList<>();

        for (String[] entry : map) {
            String code = entry[0];
            String name = entry[1];
            Category category = new Category();
            category.setCode(code);
            category.setName(name);
            categories.add(category);
        }
        categoryService.saveCategorys(categories);

        Role admin = Role.ADMIN;
        List<Member> members = new ArrayList<>();
        members.add(Member.builder()
                .email("admin@naver.com")
                .password("admin1234!")
                .nickname("관리자")
                .build());
        members.add(Member.builder()
                .email("lsh@naver.com")
                .password("lsh")
                .nickname("음악")
                .build());
        members.add(Member.builder()
                .email("test@naver.com")
                .password("test1234!")
                .nickname("테스트")
                .build());

        for (Member member : members) {
            member.setRole(admin);
            memberService.createMember(member);
        }
        Member testMember = Member.builder()
                .memberId(1L)
                .build();
        KakaoToken kakaoToken = KakaoToken.builder()
                .accessToken("tOY0hmN6A9H8RpiX2nkDCmOZECpCN-QqpNMCMqknCisNHgAAAYln0WhQ")
                .member(testMember)
                .build();
        kakaoTokenOauthService.saveTestToken(kakaoToken);

        Member member = Member.builder()
                .memberId(1L)
                .build();
        // Schedule schedule = new Schedule();
        // schedule.setRegion("제주도");
        // schedule.setTitle("즐거운 여행 제목");
        // schedule.setContent("여행 내용");
        // schedule.setMemberCount(5);
        // schedule.setStartDate(LocalDate.now());
        // schedule.setEndDate(LocalDate.now().plusDays(3));
        // schedule.setMember(member);
        // schedule.setPeriod(4);

        // Member member2 = Member.builder()
        //         .memberId(2L)
        //         .build();
        for (int i = 0; i < 32; i++) {
            // if (i > 3) {
            //     member = member2;
            // }
            Schedule schedule = new Schedule();
            schedule.setRegion("제주도");
            schedule.setTitle("즐거운 여행 제목");
            schedule.setMemberCount(5);
            schedule.setStartDate(LocalDate.now());
            schedule.setEndDate(LocalDate.now().plusDays(3));
            schedule.setMember(member);
            schedule.setPeriod(4);
            scheduleService.saveSchedule(schedule);
        }

        // scheduleService.saveSchedule(schedule);

        String[] placeNames = {"감귤 농장", "초콜릿 박물관", "제주도 바닷가"};
        List<Place> places = new ArrayList<>();
        List<SchedulePlace> schedulePlaces = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Category category = categories.get(i);
            Place place = new Place();
            place.setApiId(i * 10 + i);
            place.setName(placeNames[i - 1]);
            place.setAddress("제주도 무슨동 무슨길" + i);
            place.setLatitude(String.format("%d.%d", i * 205 + i * 17 + i * 8, i * 27));
            place.setLongitude(String.format("%d.%d", i * 121 + i * 23 + i * 3, i * 31));
            place.setCategory(category);
            place.setPhone("010-0000-0000");
            places.add(place);

            Schedule newSchedule = new Schedule();
            newSchedule.setScheduleId(1L);
            Place newPlace = new Place();
            newPlace.setPlaceId(Long.valueOf(i));
            SchedulePlace schedulePlace = new SchedulePlace();
            schedulePlace.setSchedule(newSchedule);
            schedulePlace.setPlace(newPlace);
            schedulePlace.setDays(1);
            schedulePlace.setOrders(i);
            schedulePlace.setBookmark(false);
            schedulePlaces.add(schedulePlace);
        }

        placeService.savePlaces(places);
        schedulePlaceService.saveSchedulePlaces(schedulePlaces);
    }
}
