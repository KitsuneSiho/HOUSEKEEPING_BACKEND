package com.housekeeping.service.oauth2;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.DTO.oauth2.*;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.UserPlatform;
import com.housekeeping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

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

        String email = oAuth2Response.getEmail();
        String name = oAuth2Response.getName();
        final String nickname = oAuth2Response.getNickname() != null && !oAuth2Response.getNickname().trim().isEmpty()
                ? oAuth2Response.getNickname()
                : clientName + "_" + oAuth2Response.getProviderId();

        UserPlatform userPlatform = UserPlatform.valueOf(clientName.toUpperCase());

        // 사용자 정보를 DB에서 가져오거나 새로 생성
        Optional<User> userOptional = userRepository.findByEmailAndUserPlatform(email, userPlatform);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = createNewUser(email, name, nickname, userPlatform);
        }

        UserDTO userDTO = UserDTO.builder()
                .userId(user.getUserId())
                .email(email)
                .name(name)
                .nickname(user.getNickname())
                .role(user.getRole())
                .userPlatform(user.getUserPlatform())
                .build();

        return new CustomOAuth2User(userDTO);
    }

    private User createNewUser(String email, String name, String nickname, UserPlatform userPlatform) {
        User newUser = User.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .userPlatform(userPlatform)
                .role("ROLE_USER")
                .build();
        return userRepository.save(newUser);
    }
}