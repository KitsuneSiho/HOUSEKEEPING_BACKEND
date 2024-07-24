package com.housekeeping.service.implement;

import com.housekeeping.entity.ChatRoom;
import com.housekeeping.entity.Message;
import com.housekeeping.entity.Room;
import com.housekeeping.repository.ChatRoomMemberRepository;
import com.housekeeping.repository.ChatRoomRepository;
import com.housekeeping.repository.MessageRepository;
import com.housekeeping.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    public List<ChatRoom> getChatRoomsByUserId(Long userId) {

        return chatRoomMemberRepository.findChatRoomsByUserId(userId);
    }

    @Override
    public Long saveChatRoom(ChatRoom chatRoom) {
        return 0L;
    }

    @Override
    public Long inviteUser(Long chatRoomId, Long userId) {
        return 0L;
    }

    @Override
    public String quitChatRoom(Long chatRoomId, Long userId) {
        return "";
    }

    @Override
    public List<Message> getMessagesByChatRoomId(Long charRoomId) {
        return List.of();
    }

    @Override
    public Long saveMessage(Message message) {
        return 0L;
    }
}
