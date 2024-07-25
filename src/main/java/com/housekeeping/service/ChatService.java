package com.housekeeping.service;

import com.housekeeping.entity.ChatRoom;
import com.housekeeping.entity.ChatRoomMember;
import com.housekeeping.entity.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ChatService {

    ChatRoom getChatRoomById(Long ChatRoomId);

    List<ChatRoom> getChatRoomsByUserId(Long userId);

    List<String> findUserNicknamesByChatRoomId(Long chatRoomId, Long userId);

    ChatRoom saveChatRoom(ChatRoom chatRoom);

    ChatRoomMember inviteUser(Long chatRoomId, Long userId);

    void quitChatRoom(Long chatRoomId, Long userId);

    List<Message> getMessagesByChatRoomId(Long chatRoomId);

    Message saveMessage(Message message);

    void markMessageAsRead(Long messageId, Long userId);

    Long getUnreadMessageCount(Long chatRoomId, Long userId);

    List<Long> getUnreadMessageIds(Long chatRoomId, Long userId);

    void updateReadStatusTrue(Long readStatusId);
}
