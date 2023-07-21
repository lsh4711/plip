package com.server.global.auth.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Token {
	private String accessToken;
	private String refreshToken;
}
