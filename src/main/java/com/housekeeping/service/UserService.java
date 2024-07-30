package com.housekeeping.service;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.enums.UserPlatform;
import com.housekeeping.entity.user.User;
import com.housekeeping.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User completeRegistration(UserDTO userDTO) {
        User user = userRepository.findByEmailAndUserPlatform(userDTO.getEmail(), UserPlatform.valueOf(userDTO.getProvider().toUpperCase()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userRepository.existsByNickname(userDTO.getNickname())) {
            throw new RuntimeException("Nickname already exists");
        }

        user.setNickname(userDTO.getNickname());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        System.out.println("User DTO: " + userDTO);
        System.out.println("User before save: " + user);

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }
}
