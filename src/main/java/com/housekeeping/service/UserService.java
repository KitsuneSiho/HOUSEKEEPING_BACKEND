package com.housekeeping.service;

import com.housekeeping.DTO.oauth2.UserRegistrationDTO;
import com.housekeeping.entity.user.UserEntity;
import com.housekeeping.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserEntity completeRegistration(UserRegistrationDTO registrationDTO) {
        UserEntity user = userRepository.findByEmailAndProvider(registrationDTO.getEmail(), registrationDTO.getProvider())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userRepository.existsByNickname(registrationDTO.getNickname())) {
            throw new RuntimeException("Nickname already exists");
        }

        user.setNickname(registrationDTO.getNickname());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        // 기타 필요한 정보 설정

        return userRepository.save(user);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }
}