package com.housekeeping.repository.custom;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    Long findChatRoomIdByChatRoomIdAndUserId(Long chatRoomId, Long userId);
    List<Long> findChatRoomIdsByUserId(Long userId);
}
