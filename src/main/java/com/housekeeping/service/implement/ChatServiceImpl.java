package com.housekeeping.service.implement;

import com.housekeeping.DTO.ChatRoomDTO;
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
import java.util.stream.Collectors;

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
    public List<ChatRoomDTO> getChatRoomsByUserId(Long userId) {

        List<ChatRoom> chatRooms = chatRoomMemberRepository.findChatRoomsByUserId(userId);

        return chatRooms.stream()
                .map(chatRoom -> ChatRoomDTO.builder()
                        .chatRoomId(chatRoom.getChatRoomId())
                        .chatRoomName(chatRoom.getChatRoomName())
                        .chatRoomType(chatRoom.getChatRoomType())
                        .chatRoomCreatedAt(chatRoom.getChatRoomCreatedAt())
                        .nickNameList(findUserNicknamesByChatRoomId(chatRoom.getChatRoomId(), userId))
                        .unreadMessageCount(getUnreadMessageCount(chatRoom.getChatRoomId(), userId))
                        .build())
                .collect(Collectors.toList());
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

        MessageReadStatus readStatus = messageReadStatusRepository.findByMessageIdAndUserId(messageId, userId);

        if (readStatus != null) {
            readStatus.setRead(true);
            readStatus.setReadTimestamp(LocalDateTime.now());
            messageReadStatusRepository.save(readStatus);
        }
    }

    @Override
    public Long getUnreadMessageCount(Long chatRoomId, Long userId) {
        return messageReadStatusRepository.countUnreadMessagesByChatRoomIdAndUserId(chatRoomId, userId);
    }

    @Override
    public List<Long> getUnreadMessageIds(Long chatRoomId, Long userId) {
        return messageReadStatusRepository.findUnreadMessageIdsByChatRoomIdAndUserId(chatRoomId, userId);
    }

    @Override
    public void updateReadStatusTrue(Long readStatusId) {

        MessageReadStatus readStatus = messageReadStatusRepository.findById(readStatusId).orElseThrow();
        readStatus.setRead(true);
        readStatus.setReadTimestamp(LocalDateTime.now());
        messageReadStatusRepository.save(readStatus);
    }
}
