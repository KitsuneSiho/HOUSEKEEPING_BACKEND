package com.housekeeping.service;

import com.housekeeping.jwt.JWTUtil;
import com.housekeeping.repository.RefreshRepository;
import com.housekeeping.util.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ReissueService {
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final RefreshTokenService refreshTokenService;

    public ReissueService(JWTUtil jwtUtil, RefreshRepository refreshRepository, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();

        refresh = Arrays.stream(cookies)
                .filter((cookie) -> cookie.getName().equals("refresh"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (refresh == null) {
            return new ResponseEntity<>("refresh token is null", HttpStatus.BAD_REQUEST);
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch(ExpiredJwtException e){
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);
        if(!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);
        Long userId = jwtUtil.getUserId(refresh);
        String nickname = jwtUtil.getNickname(refresh);

        Boolean isExist = refreshRepository.existsByRefresh(refresh);

        if(!isExist) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String newAccess = jwtUtil.createJwt("access", username, role, userId, nickname, 60 * 10 * 1000L);
        Integer expiredS = 60 * 60 * 24;
        String newRefresh = jwtUtil.createJwt("refresh", username, role, userId, nickname, expiredS * 1000L);

        refreshRepository.deleteByRefresh(refresh);
        refreshTokenService.saveRefresh(username, expiredS, newRefresh);

        response.setHeader("access", newAccess);
        response.addCookie(CookieUtil.createCookie("refresh", newRefresh, expiredS));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}