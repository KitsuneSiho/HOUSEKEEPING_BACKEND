package com.housekeeping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .exposedHeaders("Set-Cookie", "access", "Authorization")
                //.allowedOrigins("http://localhost:5173", "http://localhost:3000", "http://192.168.0.42:5000")
                .allowedOriginPatterns("*") // 모든 오리진 패턴 허용
                //.allowedOrigins("*") // 모든 오리진 허용(지워야함)
                .exposedHeaders("Set-Cookie", "access")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true) // 자격 증명을 허용하도록 설정
                .maxAge(3600L);
    }
}
