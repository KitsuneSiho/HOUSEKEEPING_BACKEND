package com.housekeeping.DTO.oauth2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class JoinDto {
    private String username;
    private String password;

    public JoinDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
