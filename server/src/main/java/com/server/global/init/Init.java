package com.server.global.init;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RestController;

import com.server.domain.member.entity.Member;
import com.server.domain.member.repository.MemberRepository;
import com.server.domain.place.entity.Place;
import com.server.domain.place.service.PlaceService;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;
import com.server.domain.schedule.service.SchedulePlaceService;
import com.server.domain.schedule.service.ScheduleService;

@RestController
public class Init {
    private MemberRepository memberRepository;
    private ScheduleService scheduleService;
    private SchedulePlaceService schedulePlaceService;
    private PlaceService placeService;

    public Init(MemberRepository memberRepository,
            ScheduleService scheduleService,
            SchedulePlaceService schedulePlaceService,
            PlaceService placeService) {
        this.memberRepository = memberRepository;
        this.scheduleService = scheduleService;
        this.schedulePlaceService = schedulePlaceService;
        this.placeService = placeService;
    }

    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .email("lsh@naver.com")
                .password("lshlshlshlsh1234!@")
                .nickname("음악")
                .build();
        /**
         * @author 다영
         * 테스트 코드 오류로 service -> repository로 수정
         * */
        memberRepository.save(member);

        Member newMember = Member.builder()
                .memberId(1L)
                .build();
        Schedule schedule = new Schedule();
        schedule.setCity("제주도");
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

            /**
             * @author 지인
             * 테스트 돌리니
             * TransientPropertyValueException에러가 나서, places와 newSchedule을 먼저 저장하고
             * SchedulePlace에 set 되도록 변경함.
             * 데이터 무결성원칙때문에 자동으로 생성되는 id는 set해주면 안된다고 함.
             */

            placeService.savePlaces(places);

            Schedule newSchedule = new Schedule();

            scheduleService.saveSchedule(newSchedule);

            //newSchedule.setScheduleId(1L);
            // Place newPlace = new Place();
            //newPlace.setPlaceId(Long.valueOf(i));
            SchedulePlace schedulePlace = new SchedulePlace();
            schedulePlace.setSchedule(newSchedule);
            //schedulePlace.setPlace(newPlace);
            schedulePlace.setPlace(place);
            schedulePlace.setDays(1);
            schedulePlace.setOrders(i);
            schedulePlaces.add(schedulePlace);
        }

        schedulePlaceService.saveSchedulePlaces(schedulePlaces);

    }
}
