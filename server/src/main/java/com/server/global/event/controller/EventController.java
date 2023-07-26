package com.server.global.event.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.global.event.dto.EventResponse;
import com.server.global.event.entity.Gift;
import com.server.global.event.mapper.GiftMapper;
import com.server.global.event.service.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final GiftMapper giftMapper;

    @GetMapping("/gifts")
    public ResponseEntity getGift() {
        Gift gift = eventService.getGift();
        EventResponse eventResponse = giftMapper.giftToEventResponse(gift);

        long ranking = eventResponse.getRanking();
        String nickname = gift.getNickname();
        String message = null;
        if (ranking <= 50) {
            message = String.format("%s님 축하드립니다! %d등입니다. ㅎㅎ",
                nickname,
                ranking);
        } else {
            message = String.format("%s님 죄송합니다.. %d등으로 오셔서 사탕이 모두 소진 되었습니다..ㅠ",
                nickname,
                ranking);
        }
        eventResponse.setMessage(message);

        return ResponseEntity.ok(eventResponse);
    }
}
