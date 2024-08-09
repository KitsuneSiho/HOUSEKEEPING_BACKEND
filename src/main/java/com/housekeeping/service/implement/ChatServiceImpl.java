package com.housekeeping.service.implement;

import com.housekeeping.DTO.ChatRoomDTO;
import com.housekeeping.DTO.MessageDTO;
import com.housekeeping.entity.*;
import com.housekeeping.repository.ChatRoomMemberRepository;
import com.housekeeping.repository.ChatRoomRepository;
import com.housekeeping.repository.MessageReadStatusRepository;
import com.housekeeping.repository.MessageRepository;
import com.housekeeping.service.ChatService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<ChatRoomDTO> getChatRoomsByUserId(Long userId) {

        List<ChatRoom> chatRooms = chatRoomMemberRepository.findChatRoomsByUserId(userId);
        List<ChatRoomDTO> chatRoomDTOS = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {

            Tuple recentMessage = messageRepository.getRecentMessageByChatRoomId(chatRoom.getChatRoomId());

            ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .chatRoomName(chatRoom.getChatRoomName())
                    .chatRoomType(chatRoom.getChatRoomType())
                    .chatRoomUpdatedAt(chatRoom.getChatRoomUpdatedAt())
                    .nickNameList(chatRoomMemberRepository.findUserNicknamesByChatRoomId(chatRoom.getChatRoomId(), userId))
                    .unreadMessageCount(getUnreadMessageCount(chatRoom.getChatRoomId(), userId))
                    .build();

            if (recentMessage == null) {
                chatRoomDTO.setRecentMessage("최근 메시지가 없습니다.");
            } else {
                chatRoomDTO.setRecentMessage(recentMessage.get(QMessage.message.messageContent));
            }

            chatRoomDTOS.add(chatRoomDTO);
        }

        return chatRoomDTOS;
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
    public MessageDTO getRecentMessageByChatRoomId(Long chatRoomId) {

        Tuple recentMessage = messageRepository.getRecentMessageByChatRoomId(chatRoomId);

        if (recentMessage == null) {
            return MessageDTO.builder().messageContent("최근 메시지가 없습니다").build();
        }

        return MessageDTO.builder()
                .messageId(recentMessage.get(QMessage.message.messageId))
                .messageContent(recentMessage.get(QMessage.message.messageContent))
                .messageTimestamp(recentMessage.get(QMessage.message.messageTimestamp))
                .build();
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

    @Override
    public List<String> chatRoomUserList(Long chatRoomId, Long userId) {

        return chatRoomMemberRepository.findUserNicknamesByChatRoomId(chatRoomId, userId);
    }

    @Override
    public Long findChatRoomIdByNicknameAndUserId(Long myUserId, Long friendUserId) {

        List<Long> chatRoomIds = chatRoomRepository.findChatRoomIdsByUserId(myUserId);

        Long result = null;

        for (Long chatRoomId : chatRoomIds) {
            result = chatRoomRepository.findChatRoomIdByChatRoomIdAndUserId(chatRoomId, friendUserId);

            if (result != null) {
                return result;
            }
        }

        return null;
    }
}
