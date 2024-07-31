package com.housekeeping.customhandler;

import com.housekeeping.DTO.oauth2.CustomOAuth2User;
import com.housekeeping.jwt.JWTUtil;
import com.housekeeping.service.user.RefreshTokenService;
import com.housekeeping.service.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;

@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String name = customOAuth2User.getName();
        String email = customOAuth2User.getEmail();
        String provider = customOAuth2User.getUsername().split("_")[0];
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        boolean isNewUser = customOAuth2User.isNewUser();
        Long userId = customOAuth2User.getUserId();

        Integer expireS = 24 * 60 * 60;
        String access = jwtUtil.createJwt("access", email, provider, role, 60 * 10 * 1000L);
        String refresh = jwtUtil.createJwt("refresh", email, provider, role, expireS * 1000L);

        refreshTokenService.saveRefresh(email, expireS, refresh);

        response.addCookie(CookieUtil.createCookie("access", access, 60 * 10));
        response.addCookie(CookieUtil.createCookie("refresh", refresh, expireS));

        String encodedName = URLEncoder.encode(name, "UTF-8");
        String encodedEmail = URLEncoder.encode(email, "UTF-8");
        String encodedProvider = URLEncoder.encode(provider, "UTF-8");
        String phoneNumber = customOAuth2User.getPhoneNumber();
        String encodedPhoneNumber = phoneNumber != null ? URLEncoder.encode(phoneNumber, "UTF-8") : "";

        String redirectUrl = isNewUser
                ? String.format("http://localhost:5173/firstlogin?name=%s&email=%s&provider=%s&phoneNumber=%s&userId=%d",
                encodedName, encodedEmail, encodedProvider, encodedPhoneNumber, userId)
                : String.format("http://localhost:5173/mainpage?userId=%d", userId);

        response.sendRedirect(redirectUrl);
    }
}