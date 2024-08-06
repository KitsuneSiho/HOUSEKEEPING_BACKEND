package com.housekeeping.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * 이미 액세스 토큰이 있는 경우,
 * 내부에서 사용할 authentication 정보를 set
 */
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String access = request.getHeader("access");

        // access token null
        if (access == null) {
            filterChain.doFilter(request, response);
            return;
        }
        // access token expired
        try {
            jwtUtil.isExpired(access);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String category = jwtUtil.getCategory(access);

        // not access token
        if (!category.equals("access")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String username = jwtUtil.getUsername(access);
        String role = jwtUtil.getRole(access);
        Long userId = jwtUtil.getUserId(access);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, Collections.singleton(() -> "ROLE_" + role));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        request.setAttribute("userId", userId);

        filterChain.doFilter(request, response);
    }
}
