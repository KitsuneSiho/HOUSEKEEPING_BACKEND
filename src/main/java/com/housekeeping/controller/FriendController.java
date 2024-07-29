package com.housekeeping.controller;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.user.UserEntity;
import com.housekeeping.service.FriendService;
import com.housekeeping.service.UserService;
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
    private final UserService userService;

    @GetMapping("/list")
    public List<UserDTO> getFriends(@RequestParam("userId") Long userId) {

        return friendService.getFriends(userId);
    }

    @GetMapping("/list/online")
    public List<UserDTO> getFriendsOnline(@RequestParam("userId") Long userId) {

        return friendService.getOnlineFriends(userId);
    }

    @GetMapping("/list/online2")
    public List<UserDTO> getFriendsOnline2(@RequestParam("nickname") String nickname) {

        UserEntity user = userService.getUserByNickname(nickname);

        return friendService.getOnlineFriends(user.getUserId());
    }
}
