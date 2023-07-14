package com.server.domain.schedule.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

        if (placeLists != null) {
            schedulePlaceService.deleteSchedulePlaces(schedulePlaces);
            schedulePlaces = placeService
                    .savePlaceLists(updatedSchedule, placeLists);
        }

        List<PlaceResponse> placeResponses = placeMapper
                .schedulePlacesToPlaceResponses(schedulePlaces);
        ScheduleResponse scheduleResponse = scheduleMapper
                .scheduleToScheduleResponse(updatedSchedule);
        scheduleResponse.setPlaces(placeResponses);

        return ResponseEntity.ok(scheduleResponse);
    }

    // 일단은 장소 정보까지 넣어놈
    @GetMapping("/{scheduleId}")
    public ResponseEntity getSchedule(@PathVariable long scheduleId) {
        Schedule foundSchedule = scheduleService.findSchedule(scheduleId);
        List<SchedulePlace> schedulePlaces = foundSchedule.getSchedulePlaces();
        List<PlaceResponse> placeResponses = placeMapper
                .schedulePlacesToPlaceResponses(schedulePlaces);
        ScheduleResponse scheduleResponse = scheduleMapper
                .scheduleToScheduleResponse(foundSchedule);
        scheduleResponse.setPlaces(placeResponses);

        // 나중에 member의 모든 정보대신 공개해도 되는 정보만 포함해야함
        return ResponseEntity.ok(scheduleResponse);
    }

    @GetMapping("/{scheduleId}/places")
    public ResponseEntity getPlacesByScheduleId(@PathVariable long scheduleId) {
        Schedule foundSchedule = scheduleService.findSchedule(scheduleId);
        List<SchedulePlace> schedulePlaces = foundSchedule.getSchedulePlaces();
        List<PlaceResponse> placeResponses = placeMapper
                .schedulePlacesToPlaceResponses(schedulePlaces);

        return ResponseEntity.ok(placeResponses);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity deleteSchedule(@PathVariable long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);

        return ResponseEntity.noContent().build();
    }


    // 알림 보내는 메서드
    // @Async
    // private void sendNotifications(Schedule schedule, Long memberId, LocalDate startDate, LocalDate endDate) {
    //     Notification notification = createNotificationFromSchedule(schedule);
    //     LocalTime sendTime1 = LocalTime.of(16, 0);
    //     LocalTime sendTime2 = LocalTime.of(16, 1);
    //
    //     ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
    //     LocalDateTime currentTime = LocalDateTime.now();
    //
    //     LocalDateTime startDateTime = LocalDateTime.of(startDate, sendTime1);
    //     LocalDateTime endDateTime = LocalDateTime.of(endDate, sendTime2);
    //
    //     if (startDateTime.isAfter(currentTime)) {
    //         Duration initialDelay = Duration.between(currentTime, startDateTime);
    //
    //         executorService.schedule(() -> {
    //             // 알림 전송 작업
    //             SseEmitter emitter = emitterMap.get(memberId);
    //             try {
    //                 // emitter.send(notification, MediaType.APPLICATION_JSON);
    //                 emitter.send(SseEmitter.event().name("sse").data(notification));
    //                 System.out.println("StartDate Notification sent to memberId: " + memberId);
    //             } catch (Exception e) {
    //                 emitter.completeWithError(e);
    //             }
    //         }, initialDelay.toMillis(), TimeUnit.MILLISECONDS);
    //     }
    //
    //     if (endDateTime.isAfter(currentTime)) {
    //         Duration delay = Duration.between(currentTime, endDateTime);
    //
    //         executorService.schedule(() -> {
    //             // 알림 전송 작업
    //             SseEmitter emitter = emitterMap.get(memberId);
    //             System.out.println(emitter);
    //             try {
    //                 // emitter.send(notification, MediaType.APPLICATION_JSON);
    //                 emitter.send(SseEmitter.event().name("sse").data(notification));
    //                 System.out.println("EndDate Notification sent to memberId: " + memberId);
    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //                 emitter.completeWithError(e);
    //             } finally {
    //                 emitter.complete();
    //             }
    //         }, delay.toMillis(), TimeUnit.MILLISECONDS);
    //     }
    // }
    //
    // //알림 생성
    // private Notification createNotificationFromSchedule(Schedule schedule) {
    //     Notification notification = new Notification();
    //
    //     // 필요한 알림 필드 설정
    //     notification.setRegion(schedule.getRegion());
    //     notification.setTitle(schedule.getTitle());
    //     notification.setContent(schedule.getContent());
    //
    //
    //     return notification;
    // }

}
