package com.housekeeping.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String name;
    private String email;
    private String role;
    private String nickname;
    @JsonProperty("phone")
    private String phoneNumber;
    private String provider;
    private boolean isNewUser;
}