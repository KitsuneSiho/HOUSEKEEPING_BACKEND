package com.housekeeping.controller;

import com.housekeeping.entity.User;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/status/update")
    public ResponseEntity<String> updateStatus(@RequestParam("userId") Long userId , @RequestParam("isOnline") boolean isOnline) {

        User user = userService.getUserById(userId);
        user.setUserIsOnline(isOnline);

        userService.saveUser(user);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/status/update2")
    public ResponseEntity<String> updateStatus(@RequestParam("nickname") String nickname , @RequestParam("isOnline") boolean isOnline) {

        User user = userService.getUserByNickname(nickname);
        user.setUserIsOnline(isOnline);

        userService.saveUser(user);

        return ResponseEntity.ok().build();
    }
}
