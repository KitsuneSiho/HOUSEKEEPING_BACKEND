package com.housekeeping.customhandler;

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
        String username = customOAuth2User.getUsername();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        Integer expireS = 24 * 60 * 60;
        String access = jwtUtil.createJwt("access", username, role, 60 * 10 * 1000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, expireS * 1000L);

        refreshTokenService.saveRefresh(username, expireS, refresh);

        response.addCookie(CookieUtil.createCookie("access", access, 60 * 10));
        response.addCookie(CookieUtil.createCookie("refresh", refresh, expireS));

        String encodedName = URLEncoder.encode(name, "UTF-8");
        response.sendRedirect("http://localhost:5173/oauth2-jwt-header?name=" + encodedName);
    }
}
