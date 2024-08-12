package com.housekeeping.service.implement;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.LevelEXPTable;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.UserPlatform;
import com.housekeeping.repository.LevelEXPTableRepository;
import com.housekeeping.repository.UserRepository;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LevelEXPTableRepository levelEXPTableRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElse(null);
    }

    @Override
    public boolean isNewUser(String email, UserPlatform platform) {

        User user = userRepository.findByEmailAndUserPlatform(email, platform).orElseThrow();

        return (user.getNickname().startsWith("kakao_") || user.getNickname().startsWith("naver_") || user.getNickname().startsWith("google_"));
    }

    @Override
    public int getUserLevel(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return user.getLevel().getLevelLevel();
    }

    @Override
    public UserDTO completeRegistration(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByNickname(userDTO.getNickname());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Nickname already exists");
        }

        Optional<LevelEXPTable> defaultLevelOpt = levelEXPTableRepository.findByLevelLevel(1);

        if (defaultLevelOpt.isEmpty()) {
            throw new RuntimeException("Default level not found. Please check LevelEXPTable data.");
        }

        User user = User.builder()
                .username(userDTO.getUsername())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .nickname(userDTO.getNickname())
                .phoneNumber(userDTO.getPhoneNumber())
                .userPlatform(userDTO.getUserPlatform())
                .role(userDTO.getRole() != null ? userDTO.getRole() : "ROLE_USER")
                .level(defaultLevelOpt.get())
                .build();

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .userPlatform(user.getUserPlatform())
                .level(user.getLevel().getLevelLevel())
                .isNewUser(false)
                .build();
    }
}