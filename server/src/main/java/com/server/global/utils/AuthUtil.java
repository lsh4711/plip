package com.server.global.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.server.domain.member.entity.Member;
import com.server.domain.member.service.MemberService;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

public class AuthUtil {
    public static long getMemberId() {
        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        if (authentication == null) {
            throw new CustomException(ExceptionCode.UNKNOWN_USER);
        }

        long memberId = Long.parseLong(authentication.getCredentials()
            .toString());

        return memberId;
    }

    public static Member getMember(MemberService memberService) {
        long memberId = getMemberId();
        Member member = memberService.findMember(memberId);

        return member;
    }
}
