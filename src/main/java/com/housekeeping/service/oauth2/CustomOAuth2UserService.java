package com.housekeeping.service.oauth2;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.DTO.oauth2.*;
import com.housekeeping.entity.LevelEXPTable;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.Role;
import com.housekeeping.entity.enums.UserPlatform;
import com.housekeeping.repository.LevelEXPTableRepository;
import com.housekeeping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final LevelEXPTableRepository levelEXPTableRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();

        OAuth2Response response = null;
        Map<String, Object> attributes = oAuth2User.getAttributes();

        if (clientName.equalsIgnoreCase("naver")) {
            response = new NaverResponse(attributes);
        } else if (clientName.equalsIgnoreCase("google")) {
            response = new GoogleResponse(attributes);
        } else if (clientName.equalsIgnoreCase("kakao")) {
            response = new KakaoResponse(attributes);
        } else {
            throw new OAuth2AuthenticationException("Unsupported OAuth2 provider");
        }

        String providerId = response.getProviderId();
        String provider = response.getProvider();
        String nickname = provider + "_" + providerId;
        User userEntity = saveOrUpdateUser(response, nickname, provider);

        UserDTO oAuth2UserDto = UserDTO.builder()
                .userId(userEntity.getUserId())
                .username(nickname)
                .name(response.getName())
                .email(response.getEmail())
                .phoneNumber(response.getPhoneNumber())
                .role(userEntity.getRole().name())
                .build();

        return new CustomOAuth2User(oAuth2UserDto);
    }

    private User saveOrUpdateUser(OAuth2Response response, String nickname, String provider) {
        User userEntity = userRepository.findByNickname(nickname);

        if (userEntity != null) {
            userEntity.setName(response.getName());
            userEntity.setEmail(response.getEmail());
            userEntity.setPhoneNumber(response.getPhoneNumber());
        } else {
            LevelEXPTable defaultLevel = getOrCreateDefaultLevel();

            userEntity = User.builder()
                    .username(nickname)
                    .name(response.getName())
                    .email(response.getEmail())
                    .phoneNumber(response.getPhoneNumber())
                    .nickname(nickname)
                    .userPlatform(UserPlatform.valueOf(provider.toUpperCase()))
                    .role(Role.USER)
                    .userEnrollment(LocalDateTime.now())
                    .level(defaultLevel)
                    .userEXP(0)
                    .build();
        }

        return userRepository.save(userEntity);
    }

    private LevelEXPTable getOrCreateDefaultLevel() {
        LevelEXPTable defaultLevel = levelEXPTableRepository.findByLevelLevel(1);
        if (defaultLevel == null) {
            defaultLevel = new LevelEXPTable();
            defaultLevel.setLevelLevel(1);
            defaultLevel.setLevelName("Beginner");
            defaultLevel.setLevelRequireEXP(0);
            defaultLevel = levelEXPTableRepository.save(defaultLevel);
        }
        return defaultLevel;
    }
}