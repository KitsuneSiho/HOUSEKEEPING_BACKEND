package com.housekeeping.service.user;

import com.housekeeping.DTO.oauth2.CustomOAuth2User;
import com.housekeeping.DTO.oauth2.OAuth2UserDto;
import com.housekeeping.entity.user.User;
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

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = extractProviderId(oAuth2User, provider);
        String email = extractEmail(oAuth2User, provider);
        String name = extractName(oAuth2User, provider);

        User user = userRepository.findByEmailAndProvider(email, provider)
                .orElse(null);

        if (user == null) {
            // 새 사용자 생성, 하지만 닉네임은 아직 설정하지 않음
            user = User.builder()
                    .email(email)
                    .name(name)
                    .provider(provider)
                    .providerId(providerId)
                    .role("ROLE_USER")
                    .build();
            userRepository.save(user);
        }

        OAuth2UserDto oAuth2UserDto = OAuth2UserDto.builder()
                .username(provider + "_" + providerId)
                .name(name)
                .email(email)
                .role(user.getRole())
                .build();

        return new CustomOAuth2User(oAuth2UserDto);
    }

    private String extractProviderId(OAuth2User oAuth2User, String provider) {
        switch (provider) {
            case "google":
                return oAuth2User.getAttribute("sub");
            case "naver":
                return ((Map<String, Object>) oAuth2User.getAttribute("response")).get("id").toString();
            case "kakao":
                return oAuth2User.getAttribute("id").toString();
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }

    private String extractEmail(OAuth2User oAuth2User, String provider) {
        switch (provider) {
            case "google":
            case "naver":
                return oAuth2User.getAttribute("email");
            case "kakao":
                return ((Map<String, Object>) oAuth2User.getAttribute("kakao_account")).get("email").toString();
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }

    private String extractName(OAuth2User oAuth2User, String provider) {
        switch (provider) {
            case "google":
                return oAuth2User.getAttribute("name");
            case "naver":
                return ((Map<String, Object>) oAuth2User.getAttribute("response")).get("name").toString();
            case "kakao":
                return ((Map<String, Object>) oAuth2User.getAttribute("properties")).get("nickname").toString();
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }
}