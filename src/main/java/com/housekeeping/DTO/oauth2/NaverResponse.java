package com.housekeeping.DTO.oauth2;

import java.util.Map;
import java.util.Optional;

public class NaverResponse implements OAuth2Response {
    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return Optional.ofNullable(attribute.get("response"))
                .map(response -> (Map<String, Object>) response)
                .map(response -> response.get("id"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getName() {
        return Optional.ofNullable(attribute.get("response"))
                .map(response -> (Map<String, Object>) response)
                .map(response -> response.get("name"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getEmail() {
        return Optional.ofNullable(attribute.get("response"))
                .map(response -> (Map<String, Object>) response)
                .map(response -> response.get("email"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getPhoneNumber() {
        return Optional.ofNullable(attribute.get("response"))
                .map(response -> (Map<String, Object>) response)
                .map(response -> response.get("mobile"))
                .map(Object::toString)
                .orElse(null);
    }
}
