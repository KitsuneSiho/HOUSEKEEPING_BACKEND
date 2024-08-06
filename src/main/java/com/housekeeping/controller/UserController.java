package com.housekeeping.controller;

import com.housekeeping.entity.UserEntity;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 번호를 이용해서 해당 유저의 로그인 상태를 업데이트
    @PutMapping("/status/update")
    public ResponseEntity<String> updateStatus(@RequestParam("userId") Long userId , @RequestParam("isOnline") boolean isOnline) {

        UserEntity user = userService.getUserById(userId);
        user.setUserIsOnline(isOnline);

        userService.saveUser(user);

        return ResponseEntity.ok().build();
    }

    // 유저 닉네임을 이용해서 해당 유저의 로그인 상태를 업데이트
    @PutMapping("/status/update2")
    public ResponseEntity<String> updateStatus(@RequestParam("nickname") String nickname , @RequestParam("isOnline") boolean isOnline) {

        UserEntity user = userService.getUserByNickname(nickname);
        user.setUserIsOnline(isOnline);

        userService.saveUser(user);

        return ResponseEntity.ok().build();
    }
}
