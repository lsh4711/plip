package com.server.domain.mail.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthMailCodeDto {
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Post {
		@NotBlank(message = "인증코드는 필수 입력 값입니다.")
		private String authCode;
		@Pattern(regexp = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$", message = "이메일 형식이 올바르지 않습니다.")
		@NotBlank(message = "이메일은 필수 입력 값입니다.")
		private String email;
	}
}
