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

    public Gift getGift() {
        // Member
        Member member = AuthUtil.getMember(memberService);
        String email = member.getEmail();
        String nickname = member.getNickname();

        // Token
        Push push = member.getPush();
        KakaoToken kakaoToken = member.getKakaoToken();

        if (push == null && kakaoToken == null) {
            throw new CustomException(ExceptionCode.EVENT_TOKENS_NOT_FOUND);
        }
        if (push == null) {
            throw new CustomException(ExceptionCode.EVENT_PUSH_TOKEN_NOT_FOUND);
        }
        if (kakaoToken == null) {
            throw new CustomException(ExceptionCode.EVENT_KAKAO_TOKEN_NOT_FOUND);
        }

        Gift gift = giftRepository.findByEmail(email);

        if (gift != null) {
            long giftId = gift.getGiftId();
            gift.setNickname(nickname);
            gift.setGiftCodeImage(findGiftCodeImage(giftId));
            return giftRepository.save(gift);
        }

        gift = Gift.builder()
                .email(email)
                .nickname(nickname)
                .build();

        giftRepository.save(gift);

        long giftId = gift.getGiftId();
        gift.setGiftCodeImage(findGiftCodeImage(giftId));

        return gift;
    }

    byte[] findGiftCodeImage(long giftId) {
        if (giftId > 1) {
            return fileService.getImage(String.format("%s/0.jpg", giftDir));
        }
        return fileService.getImage(String.format("%s/%d.jpg", giftDir, giftId));
    }
}
