package com.housekeeping.service;

import com.housekeeping.entity.ChatRoom;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @Test
    public void getChatRooms() {
        log.info(chatService.getChatRoomsByUserId(1L));
    }

}
