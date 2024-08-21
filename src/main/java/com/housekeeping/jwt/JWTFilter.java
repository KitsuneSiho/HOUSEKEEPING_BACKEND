package com.housekeeping.jwt;

import com.housekeeping.entity.User;
import com.housekeeping.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JWTFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public JWTFilter(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        logger.info("Authorization header: {}", authorization);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.info("No valid authorization header found, passing to next filter");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];
        logger.info("JWT token extracted: {}", token);

        try {
            if (jwtUtil.isValid(token)) {
                Claims claims = jwtUtil.getClaims(token);
                logger.info("JWT claims: {}", claims);

                String username = claims.get("username", String.class);
                String role = claims.get("role", String.class);
                Long userId = claims.get("userId", Long.class);

                logger.info("Extracted userId: {}", userId);

                if (role == null || role.trim().isEmpty()) {
                    role = "ROLE_USER";
                }

                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found: " + userId));

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, List.of(new SimpleGrantedAuthority(role)));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                logger.info("Authentication set for user: {}", username);
            }
        } catch (JwtException e) {
            logger.error("JWT token validation failed: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (RuntimeException e) {
            logger.error("Error during authentication: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}