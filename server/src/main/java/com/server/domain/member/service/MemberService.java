package com.server.domain.member.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.domain.member.entity.Member;
import com.server.domain.member.entity.Member.Role;
import com.server.domain.member.repository.MemberRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Member createMember(Member member) {
		checkExistEmail(member);
		checkExistNickname(member);

		String encryptedPassword = passwordEncoder.encode(member.getPassword());
		member.setPassword(encryptedPassword);
		if (member.getRole() == null) { //TODO:로직
			member.setRole(Role.USER);
		}
		return memberRepository.save(member);
	}

	@Transactional(readOnly = true) //JPA의 세션 플러시 모드를 MANUAL(트랜잭션 내에서 사용자가 수동으로 flush하지 않으면 flush가 자동으로 수행되지 않는 모드)로 설정.
	public void checkExistNickname(Member member) {
		if (memberRepository.existsByNickname(member.getNickname()))
			throw new CustomException(ExceptionCode.NICKNAME_EXISTS);
	}

	@Transactional(readOnly = true)
	public void checkExistEmail(Member member) {
		if (memberRepository.existsByEmail(member.getEmail()))
			throw new CustomException(ExceptionCode.EMAIL_EXISTS);
	}

	public void deleteMember(String email) {
		Member member = findMemberByEmail(email);
		memberRepository.delete(member);
	}

	@Transactional(readOnly = true)
	public Member findMember(long memberId) {
		Optional<Member> optionalMember = memberRepository.findById(memberId);

		return optionalMember.orElseThrow(
			() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
	}

	//회원이메일로 등록된 회원인지 검증
	@Transactional(readOnly = true)
	public Member findMemberByEmail(String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
	}

	public Member updateMember(String name, Member patchMember) {
		Member member = findMemberByEmail(name);

		Optional.ofNullable(patchMember.getPassword())
			.ifPresent(password -> member.setPassword(passwordEncoder.encode(password)));
		Optional.ofNullable(patchMember.getNickname())
			.ifPresent(member::setNickname);
		return member;
	}

	public Member updatePassword(Member updateMember) {
		Member member = findMemberByEmail(updateMember.getEmail());
		Optional.ofNullable(updateMember.getPassword())
			.ifPresent(password -> member.setPassword(passwordEncoder.encode(password)));
		return member;
	}
}
