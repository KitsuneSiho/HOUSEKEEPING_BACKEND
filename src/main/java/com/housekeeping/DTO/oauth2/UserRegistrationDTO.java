package com.housekeeping.DTO.oauth2;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String provider;
}