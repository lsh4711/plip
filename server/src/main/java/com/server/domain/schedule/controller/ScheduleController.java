package com.server.domain.schedule.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.member.service.MemberService;
import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.entity.Place;
import com.server.domain.place.service.PlaceService;
import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;
import com.server.domain.schedule.mapper.ScheduleMapper;
import com.server.domain.schedule.service.SchedulePlaceService;
import com.server.domain.schedule.service.ScheduleService;
import com.server.global.utils.UriCreator;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    private final MemberService memberService;
    private final PlaceService placeService;
    private final SchedulePlaceService schedulePlaceService;

    @Transactional
    @PostMapping("/write")
    public ResponseEntity postSchedule(Authentication authentication,
            @Valid @RequestBody ScheduleDto.Post postDto) {
        // 요청 보낸 회원 정보 조회
        // authentication.get
        // Member foundMember = memberService.find
        Schedule schedule = scheduleMapper.postDtoToSchedule(postDto);
        // schedule.setMember(foundMember);

        List<PlaceDto.Post> placeDtos = postDto.getPlaceDtos();
        List<Place> places = placeService.placeDtosToPlaces(placeDtos);
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        List<Place> savedPlaces = placeService.savePlaces(places);
        // 이제 SchedulePlace만 저장하면됨
        List<SchedulePlace> schedulePlaces = new ArrayList<>();
        for (int i = 0; i < places.size(); i++) {
            Place place = savedPlaces.get(i);
            PlaceDto.Post placeDto = placeDtos.get(i);
            SchedulePlace schedulePlace = new SchedulePlace();
            schedulePlace.setSchedule(savedSchedule);
            schedulePlace.setPlace(place);
            schedulePlace.setDays(placeDto.getDays());
            schedulePlace.setOrders(placeDto.getOrders());
            schedulePlaces.add(schedulePlace);
        }

        schedulePlaceService.saveSchedulePlaces(schedulePlaces);

        URI location = UriCreator.createUri("schedules", savedSchedule.getScheduleId());

        // if (true) throw new CustomException(ExceptionCode.TEST_CODE);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity getPlaces() {
        return null;
    }
}
