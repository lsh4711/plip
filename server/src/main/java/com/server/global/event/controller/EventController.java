package com.server.global.event.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.server.global.event.dto.EventResponse;
import com.server.global.event.dto.Notice;
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

    @GetMapping
    public ModelAndView getTest() {
        return new ModelAndView("event.html");
    }

    @GetMapping("/gifts")
    public ResponseEntity getGift() {
        Gift gift = eventService.getGift();
        EventResponse eventResponse = giftMapper.giftToEventResponse(gift);
        long ranking = eventResponse.getRanking();

        if (ranking <= 50) {
            eventResponse.setWin(true);
        } else {
            eventResponse.setWin(false);
        }

        return ResponseEntity.ok(eventResponse);
    }

    @PostMapping("/send")
    public ResponseEntity sendNotice(@RequestBody @Valid Notice notice) {
        String message = notice.getMessage();

        eventService.sendNoticePushAndKakao(message);

        return ResponseEntity.ok(String.format("\"%s\" 전송 성공", message));
    }
}
