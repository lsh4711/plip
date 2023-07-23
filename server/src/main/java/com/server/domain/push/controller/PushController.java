package com.server.domain.push.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.push.mapper.PushMapper;
import com.server.domain.push.service.PushService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pushs")
public class PushController {
    private final PushService pushService;
    private final PushMapper pushMapper;

    // @PostMapping("/write")
    // public ResponseEntity postPush(@RequestBody PushDto.Post postdto) {
    //     // one to one 양방향 해야함
    //     long memberId = AuthUtil.getMemberId();
    //     Member member = Member.builder()
    //             .memberId(memberId)
    //             .build();

    //     Push push = pushMapper.postDtoToPush(postdto);
    //     push.setMember(member);
    //     Push savedPush = pushService.savePush(push);
    //     URI location = UriCreator.createUri("/api/pushs",
    //         savedPush.getPushId());

    //     return ResponseEntity.created(location).build();
    // }
}
