package com.server.global.auth.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.server.domain.member.entity.Member;
import com.server.domain.member.repository.MemberRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MemberDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member findMember = memberRepository.findByEmail(username)
			.orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
		return new MemberDetails(findMember);
	}
}
