package com.housekeeping.DTO.oauth2;

import com.housekeeping.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final UserDTO userDTO;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> userDTO.getRole());
        return collection;
    }

    @Override
    public String getName() {
        return userDTO.getName();
    }

    public String getUsername() {
        return userDTO.getUsername();
    }

    public String getEmail() {
        return userDTO.getEmail();
    }

    public String getPhoneNumber() {
        return userDTO.getPhoneNumber();
    }

    public boolean isNewUser() {  // 새로운 메서드 추가
        return userDTO.isNewUser();
    }
}