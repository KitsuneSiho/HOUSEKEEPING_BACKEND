package com.housekeeping.customhandler;

import com.housekeeping.DTO.oauth2.CustomOAuth2User;
import com.housekeeping.jwt.JWTUtil;
import com.housekeeping.service.RefreshTokenService;
import com.housekeeping.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;

@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2SuccessHandler.class);
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String name = customOAuth2User.getName();
        String email = customOAuth2User.getEmail();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        Long userId = customOAuth2User.getUserId();

        logger.debug("Name: {}, Email: {}, Role: {}, UserId: {}", name, email, role, userId);

        String nickname = customOAuth2User.getNickname();

        if (nickname == null || nickname.isEmpty()) {
            logger.info("New user detected. Redirecting to FirstLogin page.");
            // 임시 토큰 생성 (짧은 유효 기간)
            String tempToken = jwtUtil.createJwt("temp", email, role, userId, 5 * 60 * 1000L); // 5분

            String redirectUrl = String.format("http://localhost:5173/firstLogin?token=%s&email=%s&name=%s",
                    URLEncoder.encode(tempToken, "UTF-8"),
                    URLEncoder.encode(email, "UTF-8"),
                    URLEncoder.encode(name, "UTF-8"));

            response.sendRedirect(redirectUrl);
            return;
        }

        // 기존 사용자 처리
        Integer expireS = 24 * 60 * 60;
        String access = jwtUtil.createJwt("access", nickname, role, userId, 60 * 10 * 1000L);
        String refresh = jwtUtil.createJwt("refresh", nickname, role, userId, expireS * 1000L);

        refreshTokenService.saveRefresh(nickname, expireS, refresh);

        response.addCookie(CookieUtil.createCookie("access", access, 60 * 10));
        response.addCookie(CookieUtil.createCookie("refresh", refresh, expireS));

        String encodedName = URLEncoder.encode(name, "UTF-8");
        response.sendRedirect("http://localhost:5173/oauth2-jwt-header?name=" + encodedName);
    }
}