package com.server.global.init;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.member.entity.Member;
import com.server.domain.member.service.MemberService;
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
    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final SchedulePlaceService schedulePlaceService;
    private final PlaceService placeService;

    @PostConstruct
    public void init() {
        Member member = Member.builder()
            .email("test@naver.com")
            .password("12345678a!")
            .nickname("음악")
            .build();

        memberService.createMember(member);

        Member newMember = Member.builder()
            .memberId(1L)
            .build();
        Schedule schedule = new Schedule();
        schedule.setRegion("제주도");
        schedule.setTitle("즐거운 여행 제목");
        schedule.setContent("여행 내용");
        schedule.setMemberCount(5);
        schedule.setStartDate(LocalDate.now());
        schedule.setEndDate(LocalDate.now().plusDays(3));
        schedule.setMember(newMember);

        scheduleService.saveSchedule(schedule);

        String[] placeNames = {"감귤 농장", "초콜릿 박물관", "제주도 바닷가"};
        List<Place> places = new ArrayList<>();
        List<SchedulePlace> schedulePlaces = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Place place = new Place();
            place.setApiId(i * 10 + i);
            place.setName(placeNames[i - 1]);
            place.setAddress("제주도 무슨동 무슨길" + i);
            place.setLatitude(String.format("%d.%d", i * 205 + i * 17 + i * 8, i * 27));
            place.setLongitude(String.format("%d.%d", i * 121 + i * 23 + i * 3, i * 31));
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
            schedulePlaces.add(schedulePlace);
        }

        placeService.savePlaces(places);
        schedulePlaceService.saveSchedulePlaces(schedulePlaces);
    }
}