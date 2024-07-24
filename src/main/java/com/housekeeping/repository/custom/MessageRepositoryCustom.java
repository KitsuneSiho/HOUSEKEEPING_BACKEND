package com.housekeeping.repository.custom;

import com.housekeeping.entity.Message;

import java.util.List;

public interface MessageRepositoryCustom {

    List<Message> getMessagesByChatRoomId(Long chatRoomId);
}
