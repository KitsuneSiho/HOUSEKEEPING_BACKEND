package com.housekeeping.service;

import com.housekeeping.entity.ChatRoom;
import com.housekeeping.entity.Message;
import com.housekeeping.entity.Room;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ChatService {

    List<ChatRoom> getChatRoomsByUserId(Long userId);

    Long saveChatRoom(ChatRoom chatRoom);

    Long inviteUser(Long chatRoomId, Long userId);

    String quitChatRoom(Long chatRoomId, Long userId);

    List<Message> getMessagesByChatRoomId(Long charRoomId);

    Long saveMessage(Message message);
}
