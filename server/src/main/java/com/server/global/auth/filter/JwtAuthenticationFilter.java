package com.server.global.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.domain.member.entity.Member;
import com.server.domain.token.service.RefreshTokenService;
import com.server.global.auth.dto.LoginDto;
import com.server.global.auth.jwt.DelegateTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final DelegateTokenUtil delegateTokenUtil;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        /**
         * TODO: 비밀번호 틀렸을 때 예외처리
         * */
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult) throws ServletException, IOException {
        Member member = (Member)authResult.getPrincipal();

        String accessToken = delegateTokenUtil.delegateAccessToken(member);
        String refreshToken = delegateTokenUtil.delegateRefreshToken(member);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);
        refreshTokenService.saveTokenInfo(member.getMemberId(), refreshToken, accessToken);
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
