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

        return ResponseEntity.ok(eventResponse);
    }
}
