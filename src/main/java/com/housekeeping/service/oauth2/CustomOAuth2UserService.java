package com.housekeeping.service.oauth2;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.DTO.oauth2.*;
import com.housekeeping.entity.enums.UserPlatform;
import com.housekeeping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        logger.debug("OAuth2 attributes: {}", attributes);

        OAuth2Response response = null;
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
        String tempNickname = provider + "_" + providerId;

        boolean isNewUser = !userRepository.existsByNickname(tempNickname);

        UserDTO oAuth2UserDto = UserDTO.builder()
                .userId(null)  // 새 사용자의 경우 아직 userId가 없습니다.
                .username(tempNickname)
                .name(response.getName() != null ? response.getName() : "")
                .email(response.getEmail() != null ? response.getEmail() : "")
                .phoneNumber(response.getPhoneNumber() != null ? response.getPhoneNumber() : "")
                .role("ROLE_USER")  // 기본 역할 설정
                .userPlatform(UserPlatform.valueOf(provider.toUpperCase()))
                .isNewUser(true)  // 항상 true로 설정하여 FirstLogin 페이지로 이동
                .build();
        logger.debug("UserDTO: {}", oAuth2UserDto);

        return new CustomOAuth2User(oAuth2UserDto);
    }
}