package com.housekeeping.controller;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.LevelEXPTable;
import com.housekeeping.entity.User;
import com.housekeeping.repository.LevelEXPTableRepository;
import com.housekeeping.repository.UserRepository;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final LevelEXPTableRepository levelEXPTableRepository;
    private final UserService userService;

    @PostMapping("/complete-registration")
    public ResponseEntity<UserDTO> completeRegistration(@RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token) {
        Optional<User> existingUser = userRepository.findByNickname(userDTO.getNickname());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(400).body(null);
        }

        Optional<LevelEXPTable> defaultLevelOpt = levelEXPTableRepository.findByLevelLevel(1);

        if (defaultLevelOpt.isEmpty()) {
            return ResponseEntity.status(500).body(null);
        }


        User user = userRepository.findByEmailAndUserPlatform(userDTO.getEmail(), userDTO.getUserPlatform()).orElseThrow();

        user.setNickname(userDTO.getNickname());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setProfileImageUrl(userDTO.getProfileImageUrl());

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(userService.convertToDTO(savedUser));  // UserService의 메서드 사용
    }
}