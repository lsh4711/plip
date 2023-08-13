package com.server.domain.member.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.domain.member.entity.Member;
import com.server.domain.member.entity.Member.Role;
import com.server.domain.member.repository.MemberRepository;
import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.oauth.repository.KakaoTokenRepository;
import com.server.domain.oauth.service.KakaoApiService;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.AuthUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoApiService kakaoApiService;
    private final KakaoTokenRepository kakaoTokenRepository;

    public Member createMember(Member member) {
        checkExistEmail(member);
        checkExistNickname(member);

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        return memberRepository.save(member);
    }

    @Transactional(readOnly = true) //JPA의 세션 플러시 모드를 MANUAL(트랜잭션 내에서 사용자가 수동으로 flush하지 않으면 flush가 자동으로 수행되지 않는 모드)로 설정.
    public void checkExistNickname(Member member) {
        if (memberRepository.existsByNickname(member.getNickname()))
            throw new CustomException(ExceptionCode.NICKNAME_EXISTS);
    }

    @Transactional(readOnly = true)
    public void checkExistEmail(Member member) {
        if (memberRepository.existsByEmail(member.getEmail()))
            throw new CustomException(ExceptionCode.EMAIL_EXISTS);
    }

    // TODO: NAVER와 KAKAO를 분리해야 하는 방법을 찾아야겠음
    public void deleteMember() {
        Member member = AuthUtil.getMember(this);

        if (member.getRole().equals(Role.SOCIAL)) {
            Optional<KakaoToken> kakaoToken = kakaoTokenRepository.findByMember(member);
            if (kakaoToken.isEmpty()) {
                log.error("토큰을 찾을 수 없다. 네이버인 것 같으니 정보만 삭제하고 리턴 처리");
            } else {
                kakaoApiService.unlinkKaKaoService(kakaoToken.get().getAccessToken());
            }
        }
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public Member findMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember.orElseThrow(
            () -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    //회원이메일로 등록된 회원인지 검증
    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Member updateMember(String name, Member patchMember) {
        Member member = findMemberByEmail(name);

        Optional.ofNullable(patchMember.getPassword())
                .ifPresent(password -> member.setPassword(passwordEncoder.encode(password)));
        Optional.ofNullable(patchMember.getNickname())
                .ifPresent(member::setNickname);
        return member;
    }

    public Member updatePassword(Member updateMember) {
        Member member = findMemberByEmail(updateMember.getEmail());
        Optional.ofNullable(updateMember.getPassword())
                .ifPresent(password -> member.setPassword(passwordEncoder.encode(password)));
        return member;
    }
}
