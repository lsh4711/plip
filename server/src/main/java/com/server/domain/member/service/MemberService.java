package com.server.domain.member.service;

import org.springframework.stereotype.Service;

import com.server.domain.member.entity.Member;
import com.server.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * TODO: 이메일 중복검사, 닉네임 중복검사 필요
     *       비밀번호 암호화
     * */
    public Member createMember(Member memberDtoPostToMember) {
        return memberRepository.save(memberDtoPostToMember);
    }
}
