package com.server.domain.member.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.domain.member.entity.Member;
import com.server.domain.member.repository.MemberRepository;
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
        member.setPassword(encryptedPassword);
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

    public void deleteMember(String email) {
        Member member = findMemberByEmail(email);
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Member updateMember(String name, Member patchMember) {
        Member member = findMemberByEmail(name);
        Optional.ofNullable(patchMember.getPassword())
            .ifPresent(member::setPassword);
        Optional.ofNullable(patchMember.getNickname())
            .ifPresent(member::setNickname);
        return member;
    }

    public Member findMember(long memberId) {
        return memberRepository.findById(memberId).get();
    }
}
