package com.housekeeping.DTO.oauth2;

public interface OAuth2Response {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
    String getPhoneNumber();
    String getNickname();
}