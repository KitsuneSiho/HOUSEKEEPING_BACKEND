package com.housekeeping.customhandler;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.DTO.oauth2.CustomOAuth2User;
import com.housekeeping.jwt.JWTUtil;
import com.housekeeping.service.RefreshTokenService;
import com.housekeeping.service.UserService;
import com.housekeeping.util.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public CustomOAuth2SuccessHandler(JWTUtil jwtUtil, RefreshTokenService refreshTokenService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        UserDTO userDto = customOAuth2User.getUserDTO();

        String username = userDto.getUsername();
        String role = userDto.getRole();
        Long userId = userDto.getUserId();
        String nickname = userDto.getNickname();

        boolean isNewUser = userService.isNewUser(userDto.getEmail(), userDto.getUserPlatform());

        String accessToken = jwtUtil.createJwt("access", username, role, userId, nickname, 60 * 10 * 1000L);
        String refreshToken = jwtUtil.createJwt("refresh", username, role, userId, nickname, 60 * 60 * 24 * 1000L);

        // RefreshToken을 DB에 저장
        refreshTokenService.saveRefresh(nickname, 60 * 60 * 24, refreshToken);

        String redirectUrl;
        if (isNewUser) {
            redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/oauth2/redirect")
                    .queryParam("token", accessToken)
                    .queryParam("redirectPath", "/firstLogin")
                    .queryParam("name", URLEncoder.encode(userDto.getName(), StandardCharsets.UTF_8))
                    .queryParam("email", URLEncoder.encode(userDto.getEmail(), StandardCharsets.UTF_8))
                    .queryParam("provider", userDto.getUserPlatform().toString())
                    .build().toUriString();

            if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().isEmpty()) {
                redirectUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                        .queryParam("phoneNumber", URLEncoder.encode(userDto.getPhoneNumber(), StandardCharsets.UTF_8))
                        .build().toUriString();
            }
        } else {
            redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/oauth2/redirect")
                    .queryParam("token", accessToken)
                    .queryParam("redirectPath", "/main")
                    .build().toUriString();
        }

        // Set refresh token as HTTP-only cookie
        Cookie refreshCookie = CookieUtil.createCookie("refresh", refreshToken, 60 * 60 * 24);
        response.addCookie(refreshCookie);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}