package com.housekeeping.controller;

import com.housekeeping.service.OAuth2JwtHeaderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class OAuth2Controller {
    private final OAuth2JwtHeaderService oAuth2JwtHeaderService;

    public OAuth2Controller(OAuth2JwtHeaderService oAuth2JwtHeaderService) {
        this.oAuth2JwtHeaderService = oAuth2JwtHeaderService;
    }

    @PostMapping("/oauth2-jwt-header")
    public String oauth2JwtHeader(HttpServletRequest request, HttpServletResponse response) {
        return oAuth2JwtHeaderService.oauth2JwtHeaderSet(request, response);
    }
}