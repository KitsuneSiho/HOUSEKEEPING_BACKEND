package com.housekeeping.config;

import com.housekeeping.customhandler.CustomOAuth2SuccessHandler;
import com.housekeeping.jwt.JWTFilter;
import com.housekeeping.jwt.JWTUtil;
import com.housekeeping.repository.RefreshRepository;
import com.housekeeping.repository.UserRepository;
import com.housekeeping.service.RefreshTokenService;
import com.housekeeping.service.UserService;
import com.housekeeping.service.oauth2.CustomOAuth2UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshRepository refreshRepository;
    private final UserService userService;

    // 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 실패 시 처리 핸들러
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        };
    }

    // Spring Security 필터 체인 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserRepository userRepository) throws Exception {
        http
                // HTTP Basic 인증 및 CSRF 보호 비활성화
                .httpBasic(basic -> basic.disable())
                .csrf(csrf -> csrf.disable())

                // 폼 로그인 비활성화
                .formLogin(form -> form.disable())

                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // 로그인 페이지 설정
                        .userInfoEndpoint(userinfo -> userinfo.userService(customOAuth2UserService)) // 사용자 정보 처리 서비스 설정
                        .successHandler(new CustomOAuth2SuccessHandler(jwtUtil, refreshTokenService, userService)) // 로그인 성공 시 처리 핸들러
                        .failureHandler(authenticationFailureHandler()) // 로그인 실패 시 처리 핸들러
                        .permitAll())

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .logoutUrl("/api/auth/logout") // 로그아웃 URL 설정
                        .deleteCookies("JSESSIONID", "refresh") // 로그아웃 시 쿠키 삭제
                        .invalidateHttpSession(true) // 세션 무효화
                        .clearAuthentication(true)) // 인증 정보 제거

                // CORS 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 엔드포인트별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 공용 엔드포인트 허용
                        .requestMatchers("/", "/login", "/logout", "/oauth2-jwt-header", "/api/auth/complete-registration").permitAll()
                        .requestMatchers("/firstlogin", "/firstmain", "/firstlivingroom", "/firstroomdesign", "firsttoiletroom").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/reissue", "/reissue/socket").permitAll()

                        // ROLE_ADMIN 권한이 필요한 엔드포인트
                        .requestMatchers(HttpMethod.POST, "/api/tips/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/tips/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/tips/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/tips/**").hasAnyRole("USER")


                        // ROLE_ADMIN 권한이 필요한 엔드포인트
                        .requestMatchers(HttpMethod.POST, "/api/posts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/posts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasRole("ADMIN")

                        // ROLE_USER 이상 권한이 필요한 엔드포인트
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").hasAnyRole("USER", "ADMIN")

                        // 관리자 페이지 접근 권한 설정
                        .requestMatchers("/admin").hasRole("ADMIN")

                        // 기타 모든 요청은 인증된 사용자만 허용
                        .anyRequest().authenticated())

                // 인증되지 않은 요청에 대한 예외 처리
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        }))

                // JWT 필터 추가
                .addFilterAfter(new JWTFilter(jwtUtil, userRepository), UsernamePasswordAuthenticationFilter.class)

                // 세션 상태 관리: STATELESS로 설정 (세션을 사용하지 않음)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    // CORS 설정 빈 등록
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://re.bit-two.com", "https://socket.bit-two.com","http://192.168.0.42:5000"));
        //알림 기능 실사용시 API 대시보드에 호스팅된 서버 IP 추가
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true); // 자격 증명을 허용
        configuration.setExposedHeaders(Arrays.asList("access", "Authorization"));
        configuration.setMaxAge(3600L); // CORS 응답 캐싱 시간 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
