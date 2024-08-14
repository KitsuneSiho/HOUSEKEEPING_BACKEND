package com.housekeeping.DTO;

import com.housekeeping.entity.enums.UserPlatform;
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
    private String nickname;
    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private UserPlatform userPlatform;
    private String provider;
    private boolean isNewUser;
    private String accessToken;
    private int level;
    private String levelName;  // 새로 추가된 필드
    private int exp;           // 새로 추가된 필드
    private int nextLevelExp;  // 새로 추가된 필드
}