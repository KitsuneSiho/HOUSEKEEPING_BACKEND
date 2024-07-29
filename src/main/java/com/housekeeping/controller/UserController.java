package com.housekeeping.controller;


import com.housekeeping.DTO.oauth2.UserRegistrationDTO;
import com.housekeeping.entity.user.UserEntity;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 번호를 이용해서 해당 유저의 로그인 상태를 업데이트
    @PostMapping("/complete-registration")
    public ResponseEntity<?> completeRegistration(@RequestBody UserRegistrationDTO registrationDTO) {
        try {
            UserEntity user = userService.completeRegistration(registrationDTO);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/update")
    public ResponseEntity<String> updateStatus(@RequestParam("userId") Long userId, @RequestParam("isOnline") boolean isOnline) {
        UserEntity user = userService.getUserById(userId);
        user.setUserIsOnline(isOnline);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    // 유저 닉네임을 이용해서 해당 유저의 로그인 상태를 업데이트
    @PutMapping("/status/update2")
    public ResponseEntity<String> updateStatus(@RequestParam("nickname") String nickname, @RequestParam("isOnline") boolean isOnline) {
        UserEntity user = userService.getUserByNickname(nickname);
        user.setUserIsOnline(isOnline);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }
}