package com.server.domain.member.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

<<<<<<< HEAD
=======

>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
import com.server.domain.member.dto.MemberDto;
import com.server.domain.member.entity.Member;
import com.server.domain.member.mapper.MemberMapper;
import com.server.domain.member.service.MemberService;
<<<<<<< HEAD
=======
import com.server.global.utils.UriCreator;
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class MemberController {
    private final String MEMBER_DEFAULT_URL = "/api/users";
    private final MemberMapper memberMapper;
    private final MemberService memberService;

<<<<<<< HEAD
=======

>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
    @PostMapping("/signup")
    public ResponseEntity<?> postMember(@Valid @RequestBody MemberDto.Post request) {
        Member createMember = memberService.createMember(memberMapper.memberDtoPostToMember(request));
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, createMember.getMemberId());
        return ResponseEntity.created(location).build();
    }
<<<<<<< HEAD

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
=======
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
}

