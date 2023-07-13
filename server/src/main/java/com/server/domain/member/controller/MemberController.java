package com.server.domain.member.controller;

import java.net.URI;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.member.dto.MemberDto;
import com.server.domain.member.entity.Member;
import com.server.domain.member.mapper.MemberMapper;
import com.server.domain.member.service.MemberService;
import com.server.global.dto.SingleResponseDto;
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

    @GetMapping
    public ResponseEntity<?> getMember(Principal principal) {
        Member member = memberService.findMemberByEmail(principal.getName());
        return new ResponseEntity<>(
            new SingleResponseDto<>(memberMapper.memberToMemberDtoResponse(member)), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> patchMember(@RequestBody MemberDto.Patch request, Principal principal) {
        Member updataMember = memberService.updateMember(principal.getName(),
            memberMapper.memberDtoPatchToMember(request));
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, updataMember.getMemberId());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMember(Principal principal) {
        memberService.deleteMember(principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<?> patchPasswordMember(@RequestBody @Valid MemberDto.PasswordPatch request) {
        memberService.updatePassword(memberMapper.memberDtoPasswordPatchToMember(request));
        return ResponseEntity.ok().build();
    }

}
