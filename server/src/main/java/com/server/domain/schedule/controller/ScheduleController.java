package com.server.domain.schedule.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.server.domain.mail.service.MailService;
import com.server.domain.member.entity.Member;
import com.server.domain.member.service.MemberService;
import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.dto.PlaceResponse;
import com.server.domain.place.entity.Place;
import com.server.domain.place.mapper.PlaceMapper;
import com.server.domain.place.service.PlaceService;
import com.server.domain.record.dto.RecordDto;
import com.server.domain.record.entity.Record;
import com.server.domain.record.mapper.RecordMapper;
import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.dto.ScheduleResponse;
import com.server.domain.schedule.dto.ScheduleShareResponse;
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

    private final MemberService memberService;

    private final PlaceService placeService;
    private final PlaceMapper placeMapper;

    private final SchedulePlaceService schedulePlaceService;
    private final MailService mailService;

    private final RecordMapper recordMapper;

    @Transactional
    @PostMapping("/write")
    public ResponseEntity postSchedule(@Valid @RequestBody ScheduleDto.Post postDto) {
        long memberId = CustomUtil.getAuthId();
        Member member = memberService.findMember(memberId);
        Schedule schedule = scheduleMapper.postDtoToSchedule(postDto);
        schedule.setMember(member);

        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        List<List<PlaceDto.Post>> placeDtoLists = postDto.getPlaces();
        List<List<Place>> placeLists = placeMapper.postDtoListsToPlaceLists(placeDtoLists);

        placeService.savePlaceLists(savedSchedule, placeLists);

        URI location = UriCreator.createUri("/api/schedules",
            savedSchedule.getScheduleId());

        // 비동기 알림 전송
        scheduleService.sendKakaoMessage(savedSchedule, member); // 카카오 메시지
        mailService.sendScheduleMail(savedSchedule, member); // 이메일

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
            scheduleResponses.add(scheduleResponse);
        }

        return ResponseEntity.ok(scheduleResponses);
    }

    // 일정 공유 기능이므로 민감한 정보 노출 금지
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

        Map<Long, List<RecordDto.Response>> map = new HashMap<>();

        for (SchedulePlace schedulePlace : schedulePlaces) {
            long schedulePlaceId = schedulePlace.getSchedulePlaceId();
            List<Record> records = schedulePlace.getRecords();
            List<RecordDto.Response> recordResponses = recordMapper
                    .recordsToRecordResponses(records);
            map.put(schedulePlaceId, recordResponses);
        }

        ScheduleShareResponse scheduleShareResponse = ScheduleShareResponse.builder()
                .schedule(scheduleResponse)
                .recordsMap(map)
                .build();

        return ResponseEntity.ok(scheduleShareResponse);
    }

    @GetMapping("/{scheduleId}/places")
    public ResponseEntity getPlacesByScheduleId(@PathVariable long scheduleId) {
        Schedule foundSchedule = scheduleService.findSchedule(scheduleId);
        List<SchedulePlace> schedulePlaces = foundSchedule.getSchedulePlaces();
        List<List<PlaceResponse>> placeResponseLists = placeMapper
                .schedulePlacesToPlaceResponseLists(schedulePlaces, foundSchedule);

        return ResponseEntity.ok(placeResponseLists); // size 정보는 없는 상태
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity deleteSchedule(@PathVariable long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);

        return ResponseEntity.noContent().build();
    }
}
