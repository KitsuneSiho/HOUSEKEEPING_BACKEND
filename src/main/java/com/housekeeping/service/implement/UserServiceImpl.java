package com.housekeeping.service.implement;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.User;
import com.housekeeping.repository.UserRepository;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
        return userRepository.findByNickname(nickname);
    }

    @Override
    public UserDTO completeRegistration(UserDTO userDTO) {
        // 닉네임 중복 체크
        if (userRepository.existsByNickname(userDTO.getNickname())) {
            throw new RuntimeException("Nickname already exists");
        }

        // 새 사용자 정보 저장 또는 기존 사용자 정보 업데이트
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElse(new User());

        user.setNickname(userDTO.getNickname());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUserPlatform(userDTO.getUserPlatform());
        // 기타 필요한 필드 설정...

        user = userRepository.save(user);

        // 저장된 사용자 정보를 DTO로 변환하여 반환
        return convertToDTO(user);
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .userPlatform(user.getUserPlatform())
                .isNewUser(false)
                .build();
    }
}