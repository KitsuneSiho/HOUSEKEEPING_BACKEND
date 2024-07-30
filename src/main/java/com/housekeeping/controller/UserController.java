package com.housekeeping.controller;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.user.User;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/complete-registration")
    public ResponseEntity<?> completeRegistration(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.completeRegistration(userDTO);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/update")
    public ResponseEntity<String> updateStatus(@RequestParam("userId") Long userId, @RequestParam("isOnline") boolean isOnline) {
        User user = userService.getUserById(userId);
        user.setUserIsOnline(isOnline);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/status/update2")
    public ResponseEntity<String> updateStatus(@RequestParam("nickname") String nickname, @RequestParam("isOnline") boolean isOnline) {
        User user = userService.getUserByNickname(nickname);
        user.setUserIsOnline(isOnline);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }
}
