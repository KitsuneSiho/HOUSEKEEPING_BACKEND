package com.housekeeping.service.implement;

import com.housekeeping.entity.*;
import com.housekeeping.repository.ChatRoomMemberRepository;
import com.housekeeping.repository.ChatRoomRepository;
import com.housekeeping.repository.MessageReadStatusRepository;
import com.housekeeping.repository.MessageRepository;
import com.housekeeping.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MessageReadStatusRepository messageReadStatusRepository;

    @Override
    public ChatRoom getChatRoomById(Long id) {
        return chatRoomRepository.findById(id).orElse(null);
    }

    @Override
    public List<ChatRoom> getChatRoomsByUserId(Long userId) {

        return chatRoomMemberRepository.findChatRoomsByUserId(userId);
    }

    @Override
    public List<String> findUserNicknamesByChatRoomId(Long chatRoomId, Long userId) {
        return chatRoomMemberRepository.findUserNicknamesByChatRoomId(chatRoomId, userId);
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

        Message savedMessage = messageRepository.save(message);

        List<User> users = chatRoomMemberRepository.findUsersByChatRoomId(message.getChatRoom().getChatRoomId());

        for (User user : users) {
            MessageReadStatus readStatus = MessageReadStatus.builder()
                    .message(savedMessage)
                    .user(user)
                    .isRead(false)
                    .build();

            if (user.getUserId().equals(message.getMessageSender().getUserId())) {
                readStatus.setRead(true);
                readStatus.setReadTimestamp(LocalDateTime.now());
            }

            messageReadStatusRepository.save(readStatus);
        }
        return savedMessage;
    }

    @Override
    public void markMessageAsRead(Long messageId, Long userId) {

//        MessageReadStatus readStatus = messageReadStatusRepository.findByMessage_MessageIdAndUser_UserId(messageId, userId);
//
//        if (readStatus != null) {
//            readStatus.setRead(true);
//            readStatus.setReadTimestamp(LocalDateTime.now());
//            messageReadStatusRepository.save(readStatus);
//        }
    }

    @Override
    public Long getUnreadMessageCount(Long chatRoomId, Long userId) {
//        return messageReadStatusRepository.findCountByChatRoomIdAndUserId(chatRoomId, userId);
        return 0L;
    }

    @Override
    public List<Long> getUnreadMessageIds(Long chatRoomId, Long userId) {
//        return messageReadStatusRepository.findIdsByChatRoomIdAndUserId(chatRoomId, userId);
        return List.of();
    }

    @Override
    public void updateReadStatusTrue(Long readStatusId) {

        MessageReadStatus readStatus = messageReadStatusRepository.findById(readStatusId).orElseThrow();
        readStatus.setRead(true);
        messageReadStatusRepository.save(readStatus);
    }
}
