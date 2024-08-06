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
    private final UserDTO oAuth2UserDto;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> oAuth2UserDto.getRole());
        return collection;
    }

    @Override
    public String getName() {
        return oAuth2UserDto.getName();
    }

    public String getUsername() {
        return oAuth2UserDto.getUsername();
    }

    public String getEmail() {
        return oAuth2UserDto.getEmail();
    }

    public String getPhoneNumber() {
        return oAuth2UserDto.getPhoneNumber();
    }
}
