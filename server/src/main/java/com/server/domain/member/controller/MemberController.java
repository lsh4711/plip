package com.server.domain.member.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.server.domain.member.dto.MemberDto;
import com.server.domain.member.entity.Member;
import com.server.domain.member.mapper.MemberMapper;
import com.server.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class MemberController {
    private final String MEMBER_DEFAULT_URL = "/api/users";
    private final MemberMapper memberMapper;
    private final MemberService memberService;


    @PostMapping("/signup")
    public ResponseEntity<?> postMember(@Valid @RequestBody MemberDto.Post request) {
        Member createMember = memberService.createMember(memberMapper.memberDtoPostToMember(request));
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, createMember.getMemberId());
        return ResponseEntity.created(location).build();
    }

    /**
     * TODO: 추후에 공통 모듈이 생기면 삭제하고 공통 모듈로 리팩토링, 없다면 내가 추가할 예정
     * */
    public static class UriCreator {
        public static URI createUri(String defaultUrl, long resourceId) {
            return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
        }
    }
}

