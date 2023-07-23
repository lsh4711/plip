package com.server.domain.push.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.member.entity.Member;
import com.server.domain.push.dto.PushDto;
import com.server.domain.push.entity.Push;
import com.server.domain.push.mapper.PushMapper;
import com.server.domain.push.service.PushService;
import com.server.global.utils.AuthUtil;
import com.server.global.utils.UriCreator;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pushs")
public class PushController {
    private final PushService pushService;
    private final PushMapper pushMapper;

    @PostMapping("/write")
    public ResponseEntity postPush(@RequestBody PushDto.Post postdto) {
        long memberId = AuthUtil.getMemberId();
        Member member = Member.builder()
                .memberId(memberId)
                .build();

        Push push = pushMapper.postDtoToPush(postdto);
        push.setMember(member);
        Push savedPush = pushService.savePush(push);
        URI location = UriCreator.createUri("/api/pushs",
            savedPush.getPushId());

        return ResponseEntity.created(location).build();
    }
}
