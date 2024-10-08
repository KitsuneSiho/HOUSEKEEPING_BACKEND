package com.housekeeping.DTO.oauth2;

import com.housekeeping.DTO.UserDTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
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
        String role = oAuth2UserDto.getRole() != null ? oAuth2UserDto.getRole() : "ROLE_USER";  // 기본 역할 설정
        collection.add(new SimpleGrantedAuthority(role));  // role.name() 대신 role 직접 사용
        return collection;
    }

    @Override
    public String getName() {
        return oAuth2UserDto.getName();
    }

    public String getNickname() {
        return oAuth2UserDto.getNickname();
    }

    public Long getUserId() {
        return oAuth2UserDto.getUserId();
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

    // 추가된 메서드
    public UserDTO getUserDTO() {
        return oAuth2UserDto;
    }
}