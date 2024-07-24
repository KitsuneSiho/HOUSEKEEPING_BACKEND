package com.housekeeping.repository.custom;

import com.housekeeping.entity.ChatRoom;

import java.util.List;

public interface ChatRoomMemberRepositoryCustom {
    List<ChatRoom> findChatRoomsByUserId(Long userId);
}
