package com.housekeeping.service;

import com.housekeeping.entity.ChatRoom;
import com.housekeeping.entity.Message;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.ChatRoomType;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Log4j2
public class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @Test
    public void getChatRoomsTest() {
        log.info(chatService.getChatRoomsByUserId(1L));
    }

    @Test
    public void saveChatRoomTest() {

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomType(ChatRoomType.SINGLE)
                .chatRoomCreatedAt(LocalDateTime.now())
                .build();

//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.setChatRoomType(ChatRoomType.SINGLE);

        log.info(chatService.saveChatRoom(chatRoom));
    }

    @Test
    public void inviteUserTest() {
        log.info(chatService.inviteUser(3L, 1L));
        log.info(chatService.inviteUser(3L, 4L));
    }

    @Test
    public void quitChatRoomTest() {

        chatService.quitChatRoom(3L, 1L);
        chatService.quitChatRoom(3L, 4L);
    }

    @Test
    public void getMessagesByChatRoomIdTest() {
        log.info(chatService.getMessagesByChatRoomId(1L));
    }

    @Test
    public void saveMessageTest() {
        Message message = Message.builder()
                .chatRoom(ChatRoom.builder().chatRoomId(1L).build())
                .messageSender(User.builder().userId(1L).build())
                .messageContent("첫번째 메시지")
                .messageTimestamp(LocalDateTime.now())
                .build();

        log.info(chatService.saveMessage(message));
    }

}
