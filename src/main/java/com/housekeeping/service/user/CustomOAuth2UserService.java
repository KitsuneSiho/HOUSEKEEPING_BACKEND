package com.housekeeping.service.user;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.DTO.oauth2.*;
import com.housekeeping.entity.LevelEXPTable;
import com.housekeeping.entity.enums.Role;
import com.housekeeping.entity.enums.UserPlatform;
import com.housekeeping.entity.user.User;
import com.housekeeping.repository.LevelEXPTableRepository;
import com.housekeeping.repository.user.UserRepository;
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
    private final LevelEXPTableRepository levelEXPTableRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2Response oAuth2Response = getOAuth2Response(userRequest, oAuth2User.getAttributes());

        String provider = oAuth2Response.getProvider();
        String providerId = oAuth2Response.getProviderId();
        String email = oAuth2Response.getEmail();
        String name = oAuth2Response.getName();
        String phoneNumber = oAuth2Response.getPhoneNumber();

        System.out.println("Provider: " + provider);
        System.out.println("Provider ID: " + providerId);
        System.out.println("Email: " + email);
        System.out.println("Name: " + name);
        System.out.println("Phone number from OAuth2 response: " + phoneNumber);  // 디버깅용 로그


        if (email == null || name == null) {
            throw new OAuth2AuthenticationException("Missing essential user information");
        }

        UserPlatform userPlatform;
        try {
            userPlatform = UserPlatform.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
        }

        User user = userRepository.findByEmailAndUserPlatform(email, userPlatform)
                .orElse(null);

        boolean isNewUser = false;

        if (user == null) {
            LevelEXPTable defaultLevel = levelEXPTableRepository.findById(1L)
                    .orElseGet(() -> {
                        LevelEXPTable newLevel = new LevelEXPTable();
                        newLevel.setLevelId(1L);
                        newLevel.setLevelName("Beginner");
                        newLevel.setLevelLevel(1);
                        newLevel.setLevelRequireEXP(0);
                        return levelEXPTableRepository.save(newLevel);
                    });

            String tempNickname = generateTemporaryNickname(name);
            String username = provider + "_" + providerId;

            user = User.builder()
                    .email(email)
                    .name(name)
                    .nickname(tempNickname)
                    .phoneNumber(phoneNumber)
                    .userPlatform(userPlatform)
                    .providerId(providerId)
                    .role(Role.USER)
                    .level(defaultLevel)
                    .username(username)
                    .build();
            userRepository.save(user);
            System.out.println("Saved user with phone number: " + user.getPhoneNumber());  // 디버깅용 로그
            isNewUser = true;
        }

        UserDTO userDTO = UserDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .phoneNumber(user.getPhoneNumber())
                .provider(userPlatform.name())
                .isNewUser(isNewUser)
                .build();

        return new CustomOAuth2User(userDTO);
    }

    private String generateTemporaryNickname(String name) {
        return name.replaceAll("\\s+", "") + "_" + System.currentTimeMillis() % 10000;
    }

    private OAuth2Response getOAuth2Response(OAuth2UserRequest userRequest, Map<String, Object> attributes) {
        String provider = userRequest.getClientRegistration().getRegistrationId();

        switch (provider) {
            case "google":
                return new GoogleResponse(attributes);
            case "naver":
                return new NaverResponse(attributes);
            case "kakao":
                return new KakaoResponse(attributes);
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }
}
