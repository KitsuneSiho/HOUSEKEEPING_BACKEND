package com.housekeeping.DTO;

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
    private String phoneNumber;
    private String provider;
    private boolean isNewUser;  // 새로운 필드 추가
}