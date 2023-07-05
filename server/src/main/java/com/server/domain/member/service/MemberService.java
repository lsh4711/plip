package com.server.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.domain.member.entity.Member;
import com.server.domain.member.repository.MemberRepository;

<<<<<<< HEAD
=======
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;


>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
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

<<<<<<< HEAD
    /**
     * TODO: 추후에 공통 모듈 추가되면 커스텀 익셉션으로 수정 및 익셉션 추가
     * */
    @Transactional(readOnly = true)
    public void checkExistNickname(Member member) {
        if (memberRepository.existsByNickname(member.getNickname()))
            throw new IllegalStateException("중복 닉네임 입니다.");
=======
    @Transactional(readOnly = true)
    public void checkExistNickname(Member member) {
        if (memberRepository.existsByNickname(member.getNickname()))
            throw new CustomException(ExceptionCode.NICKNAME_EXISTS);
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
    }

    @Transactional(readOnly = true)
    public void checkExistEmail(Member member) {
        if (memberRepository.existsByEmail(member.getEmail()))
<<<<<<< HEAD
            throw new IllegalStateException("중복 이메일 입니다.");
    }

    public Member findMember(long memberId) {
        return memberRepository.findById(memberId).get();
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).get();
=======
            throw new CustomException(ExceptionCode.EMAIL_EXISTS);
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
    }
}
