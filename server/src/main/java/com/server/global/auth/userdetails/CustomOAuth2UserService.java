package com.server.global.auth.userdetails;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.server.domain.mail.service.MailService;
import com.server.domain.member.entity.Member;
import com.server.domain.member.mapper.MemberMapper;
import com.server.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MailService mailService;

    //OAuth2 프로바이더로부터 인증 정보를 가져와서 사용자 정보를 로드
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
        if(optionalMember.isEmpty()) {
            mailService.sendMail(attributes.getEmail(), "welcome");
            memberRepository.save(memberMapper.oauthAttributesToMember(attributes));
        }
    }
}
