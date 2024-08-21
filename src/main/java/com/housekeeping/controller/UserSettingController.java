package com.housekeeping.controller;

import com.housekeeping.DTO.UserSettingDTO;
import com.housekeeping.entity.User;
import com.housekeeping.service.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/settings")
public class UserSettingController {

    private final UserSettingService userSettingService;

    @Autowired
    public UserSettingController(UserSettingService userSettingService) {
        this.userSettingService = userSettingService;
    }

    @GetMapping
    public ResponseEntity<UserSettingDTO> getUserSettings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            System.out.println("Current user ID: " + user.getUserId());
            UserSettingDTO settings = userSettingService.getUserSettingsById(user.getUserId());
            return ResponseEntity.ok(settings);
        } else {
            throw new RuntimeException("인증된 사용자 정보를 찾을 수 없습니다.");
        }
    }

    @PutMapping("/food-notice")
    public ResponseEntity<Void> updateFoodNoticeSetting(@RequestBody UserSettingDTO settingDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            userSettingService.updateFoodNoticeSettingById(user.getUserId(), settingDTO.isSettingFoodNotice());
            return ResponseEntity.ok().build();
        } else {
            throw new RuntimeException("인증된 사용자 정보를 찾을 수 없습니다.");
        }
    }
}