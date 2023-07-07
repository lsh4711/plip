package com.server.global.auth.userdetails;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.server.domain.member.entity.Member;
import com.server.domain.member.mapper.MemberMapper;
import com.server.domain.member.repository.MemberRepository;
import com.server.global.auth.utils.OAuthAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, oAuth2User.getAttributes());
        validateOAuth2User(attributes);
        return attributes;
    }

    /**
     * 기존 회원이 소셜 로그인도 가능하게 해야할까? 지금은 가능하게 해놓은 상태
     */
    private void validateOAuth2User(OAuthAttributes attributes) {
        Optional<Member> optionalMember = memberRepository.findByEmail(attributes.getEmail());
        optionalMember.orElseGet(
            () -> memberRepository.save(memberMapper.oauthAttributesToMember(attributes)));
    }
}
