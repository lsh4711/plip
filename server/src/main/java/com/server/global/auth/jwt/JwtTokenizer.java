package com.server.global.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Component
public class JwtTokenizer {
    @Getter
    @Value("${jwt.key.secret}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Map<String, Object> claims,
        String subject,
        Date expiration,
        String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Calendar.getInstance().getTime())
            .setExpiration(expiration)
            .signWith(key)
            .compact();
    }

    public String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(Calendar.getInstance().getTime())
            .setExpiration(expiration)
            .signWith(key)
            .compact();
    }

    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
            return claims;
        } catch (ExpiredJwtException eje) {
            throw eje;
        }

    }

    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

    public String getHeaderAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization").replace("Bearer ", "");
    }

    public String getHeaderRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Refresh"))
                return cookie.getValue();
        }
        throw new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        /*return Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equals("Refresh"))
            .findFirst()
            .orElseThrow(() -> new CustomException(
                ExceptionCode.REFRESH_TOKEN_NOT_FOUND))
            .getValue();*/
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "Bearer " + accessToken);
    }

    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("Refresh", refreshToken)
            .maxAge(60 * 60 * 24 * 3)
            .secure(true)
            .sameSite("None")
            .path("/")
            .httpOnly(true)
            .build();
        response.setHeader("Set-Cookie", cookie.toString());
    }

    public void resetHeaderRefreshToken(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("Refresh", null)
            .maxAge(0)
            .secure(true)
            .sameSite("None")
            .path("/")
            .httpOnly(true)
            .build();
        response.setHeader("Set-Cookie", cookie.toString());
    }

    public Map<String, Object> verifyJws(String jws) {
        String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());
        Map<String, Object> claims = getClaims(jws, base64EncodedSecretKey).getBody();

        return claims;
    }

    public void setAuthenticationToContext(Map<String, Object> claims) {
        List<String> roles = (List)claims.get("roles");
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles.toArray(String[]::new));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            claims.get("email"),
            claims.get("memberId"),
            authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}