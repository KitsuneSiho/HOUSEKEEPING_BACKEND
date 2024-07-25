package com.housekeeping.controller;

import com.housekeeping.DTO.ChatRoomDTO;
import com.housekeeping.DTO.MessageDTO;
import com.housekeeping.entity.ChatRoom;
import com.housekeeping.entity.Message;
import com.housekeeping.service.ChatService;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    @PostMapping("/room/create")
    public ChatRoom createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO) {

        ChatRoom result = chatService.saveChatRoom(chatRoomDTO.getChatRoom());

        for (Long userId : chatRoomDTO.getUserIdList()) {
            chatService.inviteUser(result.getChatRoomId(), userId);
        }

        return result;
    }

    @GetMapping("/room/list")
    public List<ChatRoomDTO> getChatRooms(@RequestParam("userId") Long userId) {

        List<ChatRoom> chatRooms = chatService.getChatRoomsByUserId(userId);
        List<ChatRoomDTO> chatRoomDTOs = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {

            chatRoomDTOs.add(
                    ChatRoomDTO.builder()
                            .chatRoom(chatRoom)
                            .nickNameList(chatService.findUserNicknamesByChatRoomId(chatRoom.getChatRoomId(), userId))
                            .unreadMessageCount(chatService.getUnreadMessageCount(chatRoom.getChatRoomId(), userId))
                            .build()
            );
        }

        return chatRoomDTOs;
    }

    @DeleteMapping("/room/quit")
    public ResponseEntity<String> quitChatRoom(@RequestParam("chatRoomId") Long chatRoomId, @RequestParam("userId") Long userId) {

        chatService.quitChatRoom(chatRoomId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/message/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO messageDTO) {

        Message message = new Message();

        message.setChatRoom(chatService.getChatRoomById(messageDTO.getChatRoomId()));
        message.setMessageSender(userService.getUserById(messageDTO.getMessageSenderId()));
        message.setMessageContent(messageDTO.getMessageContent());

        chatService.saveMessage(message);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/message/list")
    public List<MessageDTO> getMessages(@RequestParam("chatRoomId") Long chatRoomId) {

        List<Message> messageList = chatService.getMessagesByChatRoomId(chatRoomId);
        List<MessageDTO> messageDTOList = new ArrayList<>();

        for (Message message : messageList) {
            messageDTOList.add(
                    MessageDTO.builder()
                            .messageId(message.getMessageId())
                            .messageSenderId(message.getMessageId())
                            .messageSenderNickname(message.getMessageSender().getNickname())
                            .chatRoomId(message.getChatRoom().getChatRoomId())
                            .messageContent(message.getMessageContent())
                            .messageTimestamp(message.getMessageTimestamp())
                            .build()
            );
        }

        return messageDTOList;
    }

    @PutMapping("/message/read")
    public ResponseEntity<String> readMessage(@RequestParam("messageId") Long messageId, @RequestParam("userId") Long userId) {

        chatService.markMessageAsRead(messageId, userId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/message/read/all")
    public ResponseEntity<String> readAllMessages(@RequestParam("roomId") Long roomId, @RequestParam("userId") Long userId) {

        List<Long> readStatusIds = chatService.getUnreadMessageIds(roomId, userId);

        for (Long readStatusId : readStatusIds) {
            chatService.updateReadStatusTrue(readStatusId);
        }

        return ResponseEntity.ok().build();
    }
}
