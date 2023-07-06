package com.server.domain.member.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.domain.member.entity.Member;
import com.server.domain.member.repository.MemberRepository;

import com.server.global.exception.BusinessLogicException;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;


import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member createMember(Member member) {
        checkExistEmail(member);
        checkExistNickname(member);

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.updateEncryptedPassword(encryptedPassword);
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public void checkExistNickname(Member member) {
        if (memberRepository.existsByNickname(member.getNickname()))
            throw new CustomException(ExceptionCode.NICKNAME_EXISTS);
    }

    @Transactional(readOnly = true)
    public void checkExistEmail(Member member) {
        if (memberRepository.existsByEmail(member.getEmail()))
            throw new CustomException(ExceptionCode.EMAIL_EXISTS);
    }

    //회원이메일로 등록된 회원인지 검증
    public Member findVerifiedMember(String email){
        Optional<Member> optionalMember =
            memberRepository.findByEmail(email);
        Member findMember =
            optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }
}
