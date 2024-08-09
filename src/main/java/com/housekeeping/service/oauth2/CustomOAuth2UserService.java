package com.housekeeping.service.oauth2;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.DTO.oauth2.*;
import com.housekeeping.entity.enums.UserPlatform;
import com.housekeeping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String clientName = userRequest.getClientRegistration().getClientName().toLowerCase();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuth2Response oAuth2Response = null;

        switch (clientName) {
            case "google":
                oAuth2Response = new GoogleResponse(attributes);
                break;
            case "naver":
                oAuth2Response = new NaverResponse(attributes);
                break;
            case "kakao":
                oAuth2Response = new KakaoResponse(attributes);
                break;
            default:
                throw new OAuth2AuthenticationException("Unsupported provider: " + clientName);
        }

        String providerId = oAuth2Response.getProviderId();
        String email = oAuth2Response.getEmail();
        String name = oAuth2Response.getName();
        String phoneNumber = oAuth2Response.getPhoneNumber();

        // Generate a nickname if it's not provided
        String nickname = oAuth2Response.getNickname();
        if (nickname == null || nickname.trim().isEmpty()) {
            nickname = clientName + "_" + providerId;
        }
        String role = "ROLE_USER";

        UserDTO userDTO = UserDTO.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .role(role)
                .userPlatform(UserPlatform.valueOf(clientName.toUpperCase()))
                .build();

        return new CustomOAuth2User(userDTO);
    }
}