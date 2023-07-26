package com.server.global.event.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.server.domain.member.entity.Member;
import com.server.domain.member.service.MemberService;
import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.push.entity.Push;
import com.server.global.event.entity.Gift;
import com.server.global.event.repository.GiftRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;
import com.server.global.file.FileService;
import com.server.global.utils.AuthUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
    private final GiftRepository giftRepository;

    private final MemberService memberService;

    private final FileService fileService;

    @Value("${file.path}/gifts")
    private String giftDir;

    public String getGift() {
        // Member
        Member member = AuthUtil.getMember(memberService);
        String email = member.getEmail();

        // Token
        Push push = member.getPush();
        KakaoToken kakaoToken = member.getKakaoToken();

        if (push == null || kakaoToken == null) {
            throw new CustomException(ExceptionCode.EVENT_CONDITION_INVALID);
        }

        Gift gift = giftRepository.findByEmail(email);

        if (gift != null) {
            long giftId = gift.getGiftId();
            return findGiftCodeImage(giftId);
        }

        gift = Gift.builder()
                .email(email)
                .build();
        giftRepository.save(gift);

        long giftId = gift.getGiftId();

        return findGiftCodeImage(giftId);
    }

    String findGiftCodeImage(long giftId) {
        if (giftId > 1) {
            return fileService.getBase64EncodedImage(String.format("%s/0.jpg", giftDir));
        }
        return fileService.getBase64EncodedImage(String.format("%s/%d.jpg", giftDir, giftId));
    }
}
