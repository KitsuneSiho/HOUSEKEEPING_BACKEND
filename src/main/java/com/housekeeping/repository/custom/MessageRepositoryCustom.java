package com.housekeeping.repository.custom;

import com.housekeeping.DTO.MessageDTO;
import com.housekeeping.entity.Message;
import com.querydsl.core.Tuple;

import java.util.List;

public interface MessageRepositoryCustom {

    List<Message> getMessagesByChatRoomId(Long chatRoomId);
    Tuple getRecentMessageByChatRoomId(Long chatRoomId);
}
