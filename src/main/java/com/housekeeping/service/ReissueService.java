package com.housekeeping.service;

import com.housekeeping.entity.User;
import com.housekeeping.repository.UserRepository;
import com.housekeeping.jwt.JWTUtil;
import com.housekeeping.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReissueService {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    public ReissueService(JWTUtil jwtUtil, RefreshTokenService refreshTokenService, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<?> reissueTokens(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getCookieValue(request, "refresh");

        System.out.println("refreshToken: " + refreshToken);

        if (refreshToken == null || !jwtUtil.isValid(refreshToken)) {
            return new ResponseEntity<>("Invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        Long userId = jwtUtil.getUserId(refreshToken);
        User user = userRepository.findById(userId).orElse(null);

        if (user == null || !refreshTokenService.validateRefreshToken(userId, refreshToken)) {
            return new ResponseEntity<>("Invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String newAccessToken = jwtUtil.createAccessToken(user);
//        String newRefreshToken = jwtUtil.createRefreshToken(user);

        System.out.println("newAccessToken: " + newAccessToken);
//        System.out.println("newRefreshToken: " + newRefreshToken);

//        refreshTokenService.deleteRefreshToken(userId);
//        refreshTokenService.saveRefreshToken(userId, newRefreshToken);

        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.addCookie(CookieUtil.createCookie("refresh", refreshToken, 60 * 60 * 24 * 14));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}