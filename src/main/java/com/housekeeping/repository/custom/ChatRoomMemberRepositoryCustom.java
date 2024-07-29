package com.housekeeping.repository.custom;

import com.housekeeping.entity.ChatRoom;
import com.housekeeping.entity.ChatRoomMember;
import com.housekeeping.entity.User;

import java.util.List;

public interface ChatRoomMemberRepositoryCustom {
    List<ChatRoom> findChatRoomsByUserId(Long userId);

    ChatRoomMember findChatRoomMemberByChatRoomIdAndUserId(Long chatRoomId, Long userId);

    List<String> findUserNicknamesByChatRoomId(Long chatRoomId, Long userId);

    List<User> findUsersByChatRoomId(Long chatRoomId);
}
