package com.housekeeping.DTO.oauth2;

public interface OAuth2Response {
    String getProvider();
    String getProviderId();
    String getName();
    String getEmail();
    String getPhoneNumber();
}
