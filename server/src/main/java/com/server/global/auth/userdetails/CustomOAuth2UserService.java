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
import com.server.domain.oauth.service.KakaoApiService;
import com.server.domain.oauth.template.KakaoTemplateConstructor;
import com.server.domain.oauth.template.KakaoTemplateObject.Feed;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;
	private final MailService mailService;

	private final KakaoApiService kakaoApiService;
	private final KakaoTemplateConstructor kakaoTemplateConstructor;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, oAuth2User.getAttributes());
		String accessToken = userRequest.getAccessToken().getTokenValue();

		validateOAuth2User(attributes, accessToken);

		return attributes;
	}

	private void validateOAuth2User(OAuthAttributes attributes, String accessToken) {
		Optional<Member> optionalMember = memberRepository.findByEmail(attributes.getEmail());
		if (optionalMember.isEmpty()) {
			Member member = memberMapper.oauthAttributesToMember(attributes);
			memberRepository.save(member);

			// 비동기 알림 전송
			Feed feedTemplate = kakaoTemplateConstructor.getWelcomeTemplate(member);
			kakaoApiService.sendMessage(feedTemplate, accessToken); // 카카오 메시지
			mailService.sendMail(attributes.getEmail(), "welcome"); // 메일
		}
	}
}
