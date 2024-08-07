package com.housekeeping.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return getPayload(token).get("username", String.class);
    }

    public String getRole(String token) {
        return getPayload(token).get("role", String.class);
    }

    public String getCategory(String token) {
        return getPayload(token).get("category", String.class);
    }

    public Long getUserId(String token) {
        return getPayload(token).get("userId", Long.class);
    }

    public Boolean isExpired(String token) {
        return getPayload(token).getExpiration().before(new Date());
    }

    public String getNickname(String token) {
        return getPayload(token).get("nickname", String.class);
    }

    public String createJwt(String category, String nickname, String role, Long userId, Long expiredMs) {
        return Jwts.builder()
                .claim("category", category)
                .claim("nickname", nickname)
                .claim("role", role)
                .claim("userId", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            getPayload(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException e) {
            return false;
        }
    }
}