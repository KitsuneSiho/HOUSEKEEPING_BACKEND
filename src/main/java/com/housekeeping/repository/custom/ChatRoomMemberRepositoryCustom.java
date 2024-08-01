package com.housekeeping.repository.custom;

import com.housekeeping.entity.ChatRoom;
import com.housekeeping.entity.ChatRoomMember;
import com.housekeeping.entity.User;

import java.util.List;

public interface ChatRoomMemberRepositoryCustom {
    // 특정 회원이 들어가 있는 채팅 방 리스트를 반환
    List<ChatRoom> findChatRoomsByUserId(Long userId);
    // 특정 채팅 방과 유저 번호를 기준으로 채팅 방 멤버 엔티티를 반환
    ChatRoomMember findChatRoomMemberByChatRoomIdAndUserId(Long chatRoomId, Long userId);
    // 특정 채팅 방에 들어있는 자신을 제외한 모든 유저들의 닉네임을 반환
    List<String> findUserNicknamesByChatRoomId(Long chatRoomId, Long userId);
    // 특정 채팅 방에 들어있는 모든 유저들을 반환
    List<User> findUsersByChatRoomId(Long chatRoomId);
}
