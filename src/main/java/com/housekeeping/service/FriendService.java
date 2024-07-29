package com.housekeeping.service;

import com.housekeeping.DTO.UserDTO;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface FriendService {

    // 특정 회원의 친구 목록을 반환
    List<UserDTO> getFriends(Long userId);
    // 특정 회원의 접속 중인 친구 목록을 반환
    List<UserDTO> getOnlineFriends(Long userId);
}
