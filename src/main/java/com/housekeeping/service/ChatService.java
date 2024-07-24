package com.housekeeping.service;

import com.housekeeping.entity.ChatRoom;
import com.housekeeping.entity.ChatRoomMember;
import com.housekeeping.entity.Message;
import com.housekeeping.entity.Room;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ChatService {

    ChatRoom getChatRoomById(Long ChatRoomId);

    List<ChatRoom> getChatRoomsByUserId(Long userId);

    ChatRoom saveChatRoom(ChatRoom chatRoom);

    ChatRoomMember inviteUser(Long chatRoomId, Long userId);

    void quitChatRoom(Long chatRoomId, Long userId);

    List<Message> getMessagesByChatRoomId(Long chatRoomId);

    Message saveMessage(Message message);
}
