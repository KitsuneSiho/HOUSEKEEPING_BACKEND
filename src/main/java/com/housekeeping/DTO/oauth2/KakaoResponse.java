package com.housekeeping.DTO.oauth2;

import java.util.Map;
import java.util.Optional;

public class KakaoResponse implements OAuth2Response {
    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return Optional.ofNullable(attribute.get("id"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getName() {
        return Optional.ofNullable(attribute.get("properties"))
                .map(props -> (Map<String, Object>) props)
                .map(props -> props.get("nickname"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getEmail() {
        return Optional.ofNullable(attribute.get("kakao_account"))
                .map(account -> (Map<String, Object>) account)
                .map(account -> account.get("email"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getPhoneNumber() {
        return Optional.ofNullable(attribute.get("kakao_account"))
                .map(account -> (Map<String, Object>) account)
                .map(account -> account.get("phone_number"))
                .map(Object::toString)
                .orElse(null);
    }
}