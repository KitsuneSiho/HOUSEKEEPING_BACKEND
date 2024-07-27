package com.housekeeping.controller;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/list")
    public List<UserDTO> getFriends(@RequestParam("userId") Long userId) {

        return friendService.getFriends(userId);
    }
}
