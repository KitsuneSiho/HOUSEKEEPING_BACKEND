package com.housekeeping.controller;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileUploadController fileUploadController;


    @GetMapping("/info")
    public ResponseEntity<UserDTO> getUserInfo(@RequestParam("userId") Long userId) {
        UserDTO userDTO = userService.getUserDTOById(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody UserDTO userDTO) {
        if (userDTO.getUserId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        UserDTO updatedUser = userService.updateUserInfo(userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    // 기존의 상태 업데이트 메서드들은 유지
    @PutMapping("/status/update")
    public ResponseEntity<String> updateStatus(@RequestParam("userId") Long userId, @RequestParam("isOnline") boolean isOnline) {
        userService.updateUserStatus(userId, isOnline);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/status/update2")
    public ResponseEntity<String> updateStatus(@RequestParam("nickname") String nickname, @RequestParam("isOnline") boolean isOnline) {
        userService.updateUserStatusByNickname(nickname, isOnline);
        return ResponseEntity.ok().build();
    }

    // 유저의 아이디로 유저의 레벨을 반환
    @GetMapping("/level")
    public int getUserLevel(@RequestParam("userId") Long userId) {
        return userService.getUserLevel(userId);
    }

    @PostMapping("/update-profile-image")
    public ResponseEntity<UserDTO> updateProfileImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        try {
            ResponseEntity<String> uploadResponse = fileUploadController.uploadFile(file);
            if (uploadResponse.getStatusCode() == HttpStatus.OK) {
                String imageUrl = uploadResponse.getBody();
                UserDTO updatedUser = userService.updateProfileImage(userId, imageUrl);
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

