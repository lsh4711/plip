package com.server.domain.schedule.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.server.global.utils.CustomUtil;
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
    public ResponseEntity postSchedule(@Valid @RequestBody ScheduleDto.Post postDto) {
        long memberId = CustomUtil.getAuthId();
        Member member = Member.builder()
                .memberId(memberId)
                .build();
        Schedule schedule = scheduleMapper.postDtoToSchedule(postDto);
        schedule.setMember(member);

        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        List<List<PlaceDto.Post>> placeDtoLists = postDto.getPlaces();
        List<List<Place>> placeLists = placeMapper.postDtoListsToPlaceLists(placeDtoLists);

        placeService.savePlaceLists(savedSchedule, placeLists);

        URI location = UriCreator.createUri("/api/schedules",
            savedSchedule.getScheduleId());

        return ResponseEntity.created(location).build();
    }

    @Transactional
    @PatchMapping("/{scheduleId}/edit")
    public ResponseEntity patchSchedule(@PathVariable long scheduleId,
            @Valid @RequestBody ScheduleDto.Patch patchDto) {
        Schedule schedule = scheduleMapper.patchDtoToSchedule(patchDto);
        schedule.setScheduleId(scheduleId);

        Schedule updatedSchedule = scheduleService.updateSchedule(schedule);
        List<SchedulePlace> schedulePlaces = updatedSchedule.getSchedulePlaces();
        List<List<PlaceDto.Post>> placeDtoLists = patchDto.getPlaces();
        List<List<Place>> placeLists = placeMapper.postDtoListsToPlaceLists(placeDtoLists);

        schedulePlaceService.deleteSchedulePlaces(schedulePlaces);
        schedulePlaces = placeService
                .savePlaceLists(updatedSchedule, placeLists);

        List<List<PlaceResponse>> placeResponseLists = placeMapper
                .schedulePlacesToPlaceResponseLists(schedulePlaces, updatedSchedule);
        ScheduleResponse scheduleResponse = scheduleMapper
                .scheduleToScheduleResponse(updatedSchedule);
        scheduleResponse.setPlaces(placeResponseLists);
        scheduleResponse.setPlaceSize(schedulePlaces.size());

        return ResponseEntity.ok(scheduleResponse);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity getSchedule(@PathVariable long scheduleId) {
        Schedule foundSchedule = scheduleService.findSchedule(scheduleId);
        List<SchedulePlace> schedulePlaces = foundSchedule.getSchedulePlaces();
        List<List<PlaceResponse>> placeResponseLists = placeMapper
                .schedulePlacesToPlaceResponseLists(schedulePlaces, foundSchedule);
        ScheduleResponse scheduleResponse = scheduleMapper
                .scheduleToScheduleResponse(foundSchedule);
        scheduleResponse.setPlaces(placeResponseLists);
        scheduleResponse.setPlaceSize(schedulePlaces.size());

        // 일정 공유 기능도 겸하기에 Member의 공개해도 되는 정보만 포함해야함
        return ResponseEntity.ok(scheduleResponse);
    }

    @GetMapping
    public ResponseEntity getSchedules() {
        List<Schedule> foundSchedules = scheduleService.findSchedules();
        List<ScheduleResponse> scheduleResponses = new ArrayList<>();

        for (Schedule schedule : foundSchedules) {
            List<SchedulePlace> schedulePlaces = schedule.getSchedulePlaces();
            List<List<PlaceResponse>> placeResponseLists = placeMapper
                    .schedulePlacesToPlaceResponseLists(schedulePlaces, schedule);
            ScheduleResponse scheduleResponse = scheduleMapper
                    .scheduleToScheduleResponse(schedule);
            scheduleResponse.setPlaces(placeResponseLists);
            scheduleResponse.setPlaceSize(schedulePlaces.size());
            scheduleResponses.add(scheduleResponse);
        }

        // 일정 공유 기능도 겸하기에 Member의 공개해도 되는 정보만 포함해야함
        return ResponseEntity.ok(scheduleResponses);
    }

    @GetMapping("/{scheduleId}/share")
    public ResponseEntity getScheduleByMemberIdAndEmail(@PathVariable long scheduleId,
            @RequestParam("id") long memberId,
            @RequestParam String email) {
        Schedule foundSchedule = scheduleService.findSharedSchedule(scheduleId, memberId, email);
        List<SchedulePlace> schedulePlaces = foundSchedule.getSchedulePlaces();
        List<List<PlaceResponse>> placeResponseLists = placeMapper
                .schedulePlacesToPlaceResponseLists(schedulePlaces, foundSchedule);
        ScheduleResponse scheduleResponse = scheduleMapper
                .scheduleToScheduleResponse(foundSchedule);
        scheduleResponse.setPlaces(placeResponseLists);
        scheduleResponse.setPlaceSize(schedulePlaces.size());

        // 일정 공유 기능도 겸하기에 Member의 공개해도 되는 정보만 포함해야함
        return ResponseEntity.ok(scheduleResponse);
    }

    @GetMapping("/{scheduleId}/places")
    public ResponseEntity getPlacesByScheduleId(@PathVariable long scheduleId) {
        Schedule foundSchedule = scheduleService.findSchedule(scheduleId);
        List<SchedulePlace> schedulePlaces = foundSchedule.getSchedulePlaces();
        List<List<PlaceResponse>> placeResponseLists = placeMapper
                .schedulePlacesToPlaceResponseLists(schedulePlaces, foundSchedule);

        return ResponseEntity.ok(placeResponseLists); // size 정보 넣지않은 상태
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity deleteSchedule(@PathVariable long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);

        return ResponseEntity.noContent().build();
    }
}
