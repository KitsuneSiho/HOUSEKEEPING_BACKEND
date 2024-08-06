package com.housekeeping.controller;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.UserEntity;
import com.housekeeping.service.FriendService;
import com.housekeeping.service.GuestbookService;
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
    private final GuestbookService guestbookService;

    // 전체 친구 리스트를 반환
    @GetMapping("/list")
    public List<UserDTO> getFriends(@RequestParam("userId") Long userId) {

        return friendService.getFriends(userId);
    }

    // 유저의 번호를 기준으로 현재 접속중인 친구 리스트를 반환
    @GetMapping("/list/online")
    public List<UserDTO> getFriendsOnline(@RequestParam("userId") Long userId) {

        return friendService.getOnlineFriends(userId);
    }

    // 유저의 닉네임을 기준으로 현재 접속중인 친구 리스트를 반환
    @GetMapping("/list/online2")
    public List<UserDTO> getFriendsOnline2(@RequestParam("nickname") String nickname) {

        UserEntity user = userService.getUserByNickname(nickname);

        return friendService.getOnlineFriends(user.getUserId());
    }

    // 친구 추가를 위해 닉네임으로 사용자 검색
    @GetMapping("/search")
    public List<UserDTO> searchUsersByNickname(@RequestParam String nickname) {
        return friendService.searchUsersByNickname(nickname);
    }



}
