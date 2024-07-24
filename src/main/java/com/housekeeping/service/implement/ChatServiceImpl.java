package com.housekeeping.service.implement;

import com.housekeeping.entity.*;
import com.housekeeping.repository.ChatRoomMemberRepository;
import com.housekeeping.repository.ChatRoomRepository;
import com.housekeeping.repository.MessageRepository;
import com.housekeeping.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    public ChatRoom getChatRoomById(Long id) {
        return chatRoomRepository.findById(id).orElse(null);
    }

    @Override
    public List<ChatRoom> getChatRoomsByUserId(Long userId) {

        return chatRoomMemberRepository.findChatRoomsByUserId(userId);
    }

    @Override
    public ChatRoom saveChatRoom(ChatRoom chatRoom) {

        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoomMember inviteUser(Long chatRoomId, Long userId) {

        ChatRoomMember chatRoomMember = ChatRoomMember.builder().chatRoom(ChatRoom.builder().chatRoomId(chatRoomId).build())
                .user(User.builder().userId(userId).build()).build();

        return chatRoomMemberRepository.save(chatRoomMember);
    }

    @Override
    public void quitChatRoom(Long chatRoomId, Long userId) {

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findChatRoomMemberByChatRoomIdAndUserId(chatRoomId, userId);

        chatRoomMemberRepository.delete(chatRoomMember);
    }

    @Override
    public List<Message> getMessagesByChatRoomId(Long chatRoomId) {
        return messageRepository.getMessagesByChatRoomId(chatRoomId);
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
