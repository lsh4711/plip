package com.server.global.auth.userdetails;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.server.domain.mail.service.MailService;
import com.server.domain.member.entity.Member;
import com.server.domain.member.mapper.MemberMapper;
import com.server.domain.member.repository.MemberRepository;
import com.server.domain.oauth.service.KakaoApiService;
import com.server.domain.oauth.template.KakaoTemplateConstructor;
import com.server.domain.oauth.template.KakaoTemplateObject.Feed;
import com.server.global.auth.utils.OAuth2TokenUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MailService mailService;

    private final KakaoApiService kakaoApiService;
    private final KakaoTemplateConstructor kakaoTemplateConstructor;

    private final OAuth2TokenUtils oAuth2TokenUtils;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, oAuth2User.getAttributes());
        validateOAuth2User(attributes);
        return attributes;
    }

    private void validateOAuth2User(OAuthAttributes attributes) {
        Optional<Member> optionalMember = memberRepository.findByEmail(attributes.getEmail());
        if (optionalMember.isEmpty()) {
            Member member = memberMapper.oauthAttributesToMember(attributes);
            memberRepository.save(member);

            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2TokenUtils.getOAuth2AuthorizedClient(authentication);
            if (oAuth2TokenUtils.getOAuthRegistration(oAuth2AuthorizedClient).equals("kakao")) {
                String accessTokenValue = oAuth2TokenUtils.getOAuthAccessToken(oAuth2AuthorizedClient);
                Feed feedTemplate = kakaoTemplateConstructor
                        .getWelcomeTemplate(member);
                kakaoApiService.sendMessage(feedTemplate, accessTokenValue); // 카카오 메시지
            }
            mailService.sendMail(attributes.getEmail(), "welcome"); // 메일
        }
    }
}
