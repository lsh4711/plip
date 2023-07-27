package com.server.domain.schedule.controller;

import java.net.URI;
import java.util.ArrayList;
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
import com.server.domain.oauth.service.KakaoApiService;
import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.dto.PlaceResponse;
import com.server.domain.place.entity.Place;
import com.server.domain.place.mapper.PlaceMapper;
import com.server.domain.place.service.PlaceService;
import com.server.domain.push.service.PushService;
import com.server.domain.record.dto.RecordDto;
import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.dto.ScheduleResponse;
import com.server.domain.schedule.dto.ScheduleResponseWithRecord;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;
import com.server.domain.schedule.mapper.ScheduleMapper;
import com.server.domain.schedule.mapper.SchedulePlaceMapper;
import com.server.domain.schedule.service.SchedulePlaceService;
import com.server.domain.schedule.service.ScheduleService;
import com.server.global.utils.AuthUtil;
import com.server.global.utils.UriCreator;

import lombok.RequiredArgsConstructor;

// @Slf4j
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
    private final SchedulePlaceMapper schedulePlaceMapper;

    private final PushService pushService;
    private final KakaoApiService kakaoApiService;
    private final MailService mailService;

    // 비어있는 일정 생성 용도
    // @Transactional
    @PostMapping("/write")
    public ResponseEntity postSchedule(@Valid @RequestBody ScheduleDto.Post postDto) {
        Schedule schedule = scheduleMapper.postDtoToSchedule(postDto);
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);

        long scheduleId = savedSchedule.getScheduleId();
        URI location = UriCreator.createUri("/api/schedules", scheduleId);

        // 비동기 알림 전송
        pushService.sendPostScheduleMessage(savedSchedule); // 웹 푸시
        kakaoApiService.sendPostScheduleMessage(savedSchedule); // 카카오 메시지
        mailService.sendPostScheduleMail(savedSchedule); // 이메일

        return ResponseEntity.created(location).build();
    }

    // 일정 채우기 & 편집
    @Transactional
    @PatchMapping("/{scheduleId}/edit")
    public ResponseEntity patchSchedule(@PathVariable long scheduleId,
            @Valid @RequestBody ScheduleDto.Patch patchDto) {
        // Schedule
        Schedule schedule = scheduleMapper.patchDtoToSchedule(patchDto, scheduleId);
        Schedule updatedSchedule = scheduleService.updateSchedule(schedule); // 1. 일정 수정

        // Place
        List<List<PlaceDto.Patch>> placeDtoLists = patchDto.getPlaces();
        List<List<Place>> placeLists = placeMapper.patchDtoListsToPlaceLists(placeDtoLists);
        List<Place> places = placeService.savePlaceLists(placeLists); // 2. 장소 갱신

        // SchedulePlace
        List<SchedulePlace> schedulePlaces = updatedSchedule.getSchedulePlaces(); // 기존 (개인)장소 목록
        List<SchedulePlace> updateSchedulePlaces = placeMapper
                .placesToSchedulePlaces(places, updatedSchedule); // 변경할 (개인)장소 목록
        List<SchedulePlace> updatedSchedulePlaces = schedulePlaceService
                .updateSchedulePlaces(updateSchedulePlaces, schedulePlaces); // 3. (개인)장소 갱신

        updatedSchedule.setSchedulePlaces(updatedSchedulePlaces);

        List<List<PlaceResponse>> placeResponseLists = schedulePlaceMapper
                .schedulePlacesToPlaceResponseLists(updatedSchedule);
        ScheduleResponse scheduleResponse = scheduleMapper
                .scheduleToScheduleResponse(updatedSchedule);
        scheduleResponse.setPlaces(placeResponseLists);

        return ResponseEntity.ok(scheduleResponse);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity getSchedule(@PathVariable long scheduleId) {
        Schedule foundSchedule = scheduleService.findScheduleOfAuthMember(scheduleId);
        List<SchedulePlace> schedulePlaces = foundSchedule.getSchedulePlaces();

        List<List<PlaceResponse>> placeResponseLists = schedulePlaceMapper
                .schedulePlacesToPlaceResponseLists(foundSchedule);
        ScheduleResponse scheduleResponse = scheduleMapper
                .scheduleToScheduleResponse(foundSchedule);
        scheduleResponse.setPlaces(placeResponseLists);

        Map<Long, List<RecordDto.Response>> map = schedulePlaceMapper
                .toRecordResponseMap(schedulePlaces);

        ScheduleResponseWithRecord sharedScheduleResponse = ScheduleResponseWithRecord.builder()
                .schedule(scheduleResponse)
                .recordsMap(map)
                .build();

        return ResponseEntity.ok(sharedScheduleResponse);
    }

    @GetMapping
    public ResponseEntity getSchedules() {
        List<Schedule> foundSchedules = scheduleService.findSchedulesOfAuthMember();

        List<ScheduleResponse> scheduleResponses = new ArrayList<>();

        for (Schedule schedule : foundSchedules) {
            List<List<PlaceResponse>> placeResponseLists = schedulePlaceMapper
                    .schedulePlacesToPlaceResponseLists(schedule);
            ScheduleResponse scheduleResponse = scheduleMapper
                    .scheduleToScheduleResponse(schedule);
            scheduleResponse.setPlaces(placeResponseLists);
            scheduleResponses.add(scheduleResponse);
        }

        return ResponseEntity.ok(scheduleResponses);
    }

    // 일정 공유 기능이므로 민감한 정보 노출 금지
    @GetMapping("/{scheduleId}/share")
    public ResponseEntity getSharedSchedule(@PathVariable long scheduleId,
            @RequestParam("id") long memberId,
            @RequestParam String code) {
        Schedule foundSchedule = scheduleService.findSharedSchedule(scheduleId, memberId, code);

        List<List<PlaceResponse>> placeResponseLists = schedulePlaceMapper
                .schedulePlacesToPlaceResponseLists(foundSchedule);
        ScheduleResponse scheduleResponse = scheduleMapper
                .scheduleToScheduleResponse(foundSchedule);
        scheduleResponse.setPlaces(placeResponseLists);

        return ResponseEntity.ok(scheduleResponse);
    }

    // 공유 링크 생성, 테스트 코드 작성해야함
    @GetMapping("/{scheduleId}/share/link")
    public ResponseEntity getShareUrl(@PathVariable long scheduleId) {
        Member member = AuthUtil.getMember(memberService);

        String shareLink = scheduleService.createShareUrl(scheduleId, member);

        return ResponseEntity.ok(shareLink);
    }

    @GetMapping("/{scheduleId}/places")
    public ResponseEntity getPlacesByScheduleId(@PathVariable long scheduleId) {
        Schedule foundSchedule = scheduleService.findScheduleOfAuthMember(scheduleId);
        List<List<PlaceResponse>> placeResponseLists = schedulePlaceMapper
                .schedulePlacesToPlaceResponseLists(foundSchedule);

        return ResponseEntity.ok(placeResponseLists); // size 정보는 없는 상태
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity deleteSchedule(@PathVariable long scheduleId) {
        Schedule schedule = scheduleService.deleteSchedule(scheduleId);

        // pushService.sendPostScheduleMessage(null); // 웹 푸시
        // kakaoApiService.sendPostScheduleMessage(null); // 카카오 메시지
        // mailService.sendPostScheduleMail(null); // 이메일

        return ResponseEntity.noContent().build();
    }
}
