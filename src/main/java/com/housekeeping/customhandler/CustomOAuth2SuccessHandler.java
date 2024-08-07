package com.housekeeping.customhandler;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.DTO.oauth2.CustomOAuth2User;
import com.housekeeping.jwt.JWTUtil;
import com.housekeeping.service.RefreshTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;

    public CustomOAuth2SuccessHandler(JWTUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        UserDTO userDto = customOAuth2User.getUserDTO();

        String username = userDto.getUsername();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        Long userId = userDto.getUserId();

        // 임시 토큰 생성 (5분 유효)
        String tempToken = jwtUtil.createJwt("temp", username, role, userId, null, 5 * 60 * 1000L);

        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/firstLogin")
                .queryParam("token", tempToken)
                .queryParam("name", URLEncoder.encode(userDto.getName(), StandardCharsets.UTF_8))
                .queryParam("email", URLEncoder.encode(userDto.getEmail(), StandardCharsets.UTF_8))
                .queryParam("provider", userDto.getUserPlatform().toString())
                .build().toUriString();

        if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().isEmpty()) {
            redirectUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                    .queryParam("phoneNumber", URLEncoder.encode(userDto.getPhoneNumber(), StandardCharsets.UTF_8))
                    .build().toUriString();
        }

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}