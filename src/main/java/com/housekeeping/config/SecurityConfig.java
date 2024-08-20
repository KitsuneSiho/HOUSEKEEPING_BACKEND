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

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                System.out.println("exception = " + exception);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserRepository userRepository) throws Exception {
        // disable
        http
                .httpBasic((basic) -> basic.disable())
                .csrf((csrf) -> csrf.disable());

        // form
        http
                .formLogin((form) -> form.disable());

        // oauth2
        http
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint((userinfo) -> userinfo
                                .userService(customOAuth2UserService))
                        .successHandler(new CustomOAuth2SuccessHandler(jwtUtil, refreshTokenService, userService))
                        .failureHandler(authenticationFailureHandler())
                        .permitAll());

        // logout
        http
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .logoutUrl("/api/auth/logout")
                        .deleteCookies("JSESSIONID", "refresh")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                );

        // cors
        http
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()));

        // authorization
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login", "/logout", "/oauth2-jwt-header", "/api/auth/complete-registration").permitAll()
                .requestMatchers("/firstlogin", "/firstmain", "/firstlivingroom", "/firstroomdesign", "firsttoiletroom").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/reissue", "/reissue/socket").permitAll() // /reissue 엔드포인트를 인증 없이 접근 허용
                .requestMatchers("/api/user/**").authenticated()
                .requestMatchers("/mainpage").hasRole("USER")


                .requestMatchers(HttpMethod.POST, "/api/posts/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/posts/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()


                .requestMatchers("/admin").hasRole("ADMIN")

                .anyRequest().authenticated());

        // 인가되지 않은 사용자에 대한 exception -> 프론트엔드로 코드 응답
        http.exceptionHandling((exception) ->
                exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        }));

        // jwt filter
        http
                .addFilterAfter(new JWTFilter(jwtUtil, userRepository), UsernamePasswordAuthenticationFilter.class);

        // custom logout filter 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil, userRepository), UsernamePasswordAuthenticationFilter.class);

        // session stateless
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://192.168.0.42:5000", "https://socket.bit-two.com", "https://re.bit-two.com", "http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true); // 자격 증명을 허용하도록 설정
        configuration.setExposedHeaders(Arrays.asList("access", "Authorization"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
