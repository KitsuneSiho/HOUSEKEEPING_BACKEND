package com.housekeeping.customhandler;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.DTO.oauth2.CustomOAuth2User;
import com.housekeeping.jwt.JWTUtil;
import com.housekeeping.service.RefreshTokenService;
import com.housekeeping.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        UserDTO userDto = customOAuth2User.getUserDTO();

        handleNewUser(response, userDto);
    }

    private void handleNewUser(HttpServletResponse response, UserDTO userDto) throws IOException {
        String tempToken = jwtUtil.createJwt("temp", userDto.getEmail(), "ROLE_USER", null, 5 * 60 * 1000L); // 5분

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:5173/firstLogin")
                .queryParam("token", tempToken)
                .queryParam("name", URLEncoder.encode(userDto.getName(), StandardCharsets.UTF_8))
                .queryParam("email", URLEncoder.encode(userDto.getEmail(), StandardCharsets.UTF_8))
                .queryParam("provider", userDto.getUserPlatform().toString());

        if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().isEmpty()) {
            builder.queryParam("phoneNumber", URLEncoder.encode(userDto.getPhoneNumber(), StandardCharsets.UTF_8));
        }

        String redirectUrl = builder.build().toUriString();
        response.sendRedirect(redirectUrl);
    }


    private void handleExistingUser(HttpServletResponse response, UserDTO userDto) throws IOException {
        Long userId = userDto.getUserId();
        String role = userDto.getRole();
        String nickname = userDto.getNickname();

        Integer accessTokenExpirationSeconds = 60 * 10; // 10분
        Integer refreshTokenExpirationSeconds = 60 * 60 * 24; // 24시간

        String accessToken = jwtUtil.createJwt("access", nickname, role, userId, accessTokenExpirationSeconds * 1000L);
        String refreshToken = jwtUtil.createJwt("refresh", nickname, role, userId, refreshTokenExpirationSeconds * 1000L);

        refreshTokenService.saveRefresh(nickname, refreshTokenExpirationSeconds, refreshToken);

        response.addCookie(CookieUtil.createCookie("access", accessToken, accessTokenExpirationSeconds));
        response.addCookie(CookieUtil.createCookie("refresh", refreshToken, refreshTokenExpirationSeconds));

        response.sendRedirect("http://localhost:5173/oauth2-jwt-header");
    }
}