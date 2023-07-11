package com.server.domain.schedule.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.member.entity.Member;
import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.dto.PlaceResponse;
import com.server.domain.place.entity.Place;
import com.server.domain.place.mapper.PlaceMapper;
import com.server.domain.place.service.PlaceService;
import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.dto.ScheduleResponse;
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

    private final PlaceService placeService;
    private final PlaceMapper placeMapper;
    private final SchedulePlaceService schedulePlaceService;

    @Transactional
    @PostMapping("/write")
    public ResponseEntity postSchedule(Authentication authentication,
            @Valid @RequestBody ScheduleDto.Post postDto) {
        long memberId = Long.parseLong(authentication.getCredentials().toString());
        Member member = Member.builder()
                .memberId(memberId)
                .build();
        Schedule schedule = scheduleMapper.postDtoToSchedule(postDto);
        schedule.setMember(member);

        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        List<PlaceDto.Post> placeDtos = postDto.getPlaceDtos();
        List<Place> places = placeMapper.postDtosToPlaces(placeDtos);
        List<Place> savedPlaces = placeService.savePlaces(places);
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

        URI location = UriCreator.createUri("/api/schedules", savedSchedule.getScheduleId());

        // if (true) throw new CustomException(ExceptionCode.TEST_CODE);
        return ResponseEntity.created(location).build();
    }

    // 일단은 장소 정보까지 넣어놈
    @GetMapping("/{scheduleId}")
    public ResponseEntity getSchedule(@PathVariable("scheduleId") long scheduleId) {
        Schedule foundSchedule = scheduleService.findSchedule(scheduleId);
        List<SchedulePlace> schedulePlaces = foundSchedule.getSchedulePlaces();
        List<PlaceResponse> placeResponses = placeMapper
                .schedulePlacesToPlaceResponses(schedulePlaces);
        ScheduleResponse scheduleResponse = scheduleMapper.scheduleToScheduleResponse(foundSchedule);
        scheduleResponse.setPlaces(placeResponses);

        // 나중에 member의 모든 정보대신 공개해도 되는 정보만 포함해야함
        return new ResponseEntity<>(scheduleResponse, HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}/places")
    public ResponseEntity getPlacesByScheduleId(@PathVariable("scheduleId") long scheduleId) {
        Schedule foundSchedule = scheduleService.findSchedule(scheduleId);
        List<SchedulePlace> schedulePlaces = foundSchedule.getSchedulePlaces();
        List<PlaceResponse> placeResponses = placeMapper
                .schedulePlacesToPlaceResponses(schedulePlaces);

        return new ResponseEntity<>(placeResponses, HttpStatus.OK);
    }
}
