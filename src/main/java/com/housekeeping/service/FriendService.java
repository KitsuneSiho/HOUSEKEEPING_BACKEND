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
    // 닉네임으로 유저를 검색해서 반환
    List<UserDTO> searchUsersByNickname(String nickname);
    // 친구 목록에 친구 넣기
    void createFriendRelationship(Long userId1, Long userId2);
    // 친구 삭제
    void deleteFriendship(Long userId1, Long userId2);
}
