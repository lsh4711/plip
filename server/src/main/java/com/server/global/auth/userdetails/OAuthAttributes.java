package com.server.global.auth.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

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
public class OAuthAttributes implements OAuth2User {

	private Map<String, Object> attributes;
	private String email;
	private String nickname;
	private Member.Role role;

	public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equals("naver")) {
			return ofNaver(attributes);
		}
		return ofKakao(attributes);
	}

	private static OAuthAttributes ofNaver(Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>)attributes.get("response");

		log.info("naver response : " + response);

		return OAuthAttributes.builder()
			.email((String)response.get("email"))
			.nickname((String)response.get("nickname"))
			.attributes(attributes)
			.build();
	}

	private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>)attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>)response.get("profile");
		log.info("kakao response : " + response);
		return OAuthAttributes.builder()
			.email((String)response.get("email"))
			.nickname((String)profile.get("nickname"))
			.attributes(attributes)
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(() -> "ROLE_" + getRole());

		return collectors;
	}

	@Override
	public String getName() {
		return email;
	}
}
