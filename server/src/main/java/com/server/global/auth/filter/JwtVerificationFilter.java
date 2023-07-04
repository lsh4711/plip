package com.server.global.auth.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.server.global.auth.jwt.JwtTokenizer;
import com.server.global.auth.utils.AccessTokenRenewalUtil;
import com.server.global.auth.utils.Token;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final AccessTokenRenewalUtil accessTokenRenewalUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        try {
            Map<String, Object> claims = verifyJws(request, jws);
            setAuthenticationToContext(claims);
        } catch (ExpiredJwtException eje) {
            log.error("### 토큰이 만료됐습니다.");
            Token token = accessTokenRenewalUtil.renewAccessToken(jws);
            response.setHeader("Authorization", "Bearer " + token.getAccessToken());
            response.setHeader("Refresh", token.getRefreshToken());
        } catch (Exception e) {
            log.info("### 토큰 검증 오류 : " + e);
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request, String jws) {
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        Authentication authentication = new UsernamePasswordAuthenticationToken(claims.get("email"), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
