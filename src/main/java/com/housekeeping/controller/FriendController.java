package com.housekeeping.controller;

import lombok.RequiredArgsConstructor;

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

        User user = userService.getUserByNickname(nickname);

        return friendService.getOnlineFriends(user.getUserId());
    }
}
