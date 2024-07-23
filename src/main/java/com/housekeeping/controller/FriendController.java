package com.housekeeping.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    // 친구 목록 확인
    @GetMapping("/list")
    public void friendList() {

    }

    // 닉네임 검색
    @GetMapping("/search")
    public void friendSearch() {

    }

    // 친구 추가
    @PostMapping("/add")
    public void friendAdd() {

    }

    // 친구 삭제
    @DeleteMapping("/delete")
    public void friendDelete() {

    }


}
