package com.server.global.auth.utils;

import java.util.Map;

import com.server.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    private String nickname;
    private Member.Role role;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
        Map<String, Object> attributes) {
        if (registrationId.equals("naver")) {
            return ofNaver(userNameAttributeName, attributes);
        }
        return ofKakao(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        log.info("naver response : " + response);

        return OAuthAttributes.builder()
            .email((String)response.get("email"))
            .nickname((String)response.get("nickname"))
            .attributes(response)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        log.info("kakao response : " + response);
        /**
         * TODO: 카카오 추가 구현
         * */
        return null;
    }
}
