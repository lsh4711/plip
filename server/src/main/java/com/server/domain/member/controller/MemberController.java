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
import com.server.global.utils.UriCreator;

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
}

