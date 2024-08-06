package com.housekeeping.controller;

import com.housekeeping.DTO.ChatRoomDTO;
import com.housekeeping.DTO.InviteDTO;
import com.housekeeping.DTO.MessageDTO;
import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.ChatRoom;
import com.housekeeping.entity.Message;
import com.housekeeping.service.ChatService;
import com.housekeeping.service.FriendService;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;
    private final FriendService friendService;

    // 채팅 방 생성
    @PostMapping("/room/create")
    public ChatRoomDTO createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO) {

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomName(chatRoomDTO.getChatRoomName())
                .chatRoomType(chatRoomDTO.getChatRoomType())
                .chatRoomUpdatedAt(LocalDateTime.now())
                .build();

        ChatRoom result = chatService.saveChatRoom(chatRoom);

        for (Long userId : chatRoomDTO.getUserIdList()) {
            chatService.inviteUser(result.getChatRoomId(), userId);
        }

        return ChatRoomDTO.builder()
                .chatRoomId(result.getChatRoomId())
                .build();
    }

    // 채팅 방 정보 반환
    @GetMapping("/room/{chatRoomId}")
    public ChatRoomDTO getChatRoom(@PathVariable Long chatRoomId) {

        ChatRoom chatRoom = chatService.getChatRoomById(chatRoomId);

        return ChatRoomDTO.builder()
                .chatRoomName(chatRoom.getChatRoomName())
                .chatRoomType(chatRoom.getChatRoomType())
                .chatRoomId(chatRoom.getChatRoomId())
                .build();
    }

    // 채팅 방 리스트 반환
    @GetMapping("/room/list")
    public List<ChatRoomDTO> getChatRooms(@RequestParam("userId") Long userId) {

        return chatService.getChatRoomsByUserId(userId);
    }

    // 채팅 방에 있는 사람들의 목록과 채팅 방에 없는 내 친구들을 반환
    @GetMapping("/room/invite/list")
    public Map<String, List<UserDTO>> getChatRoomInviteList(@RequestParam("chatRoomId") Long chatRoomId, @RequestParam("userId") Long userId) {

        Map<String, List<UserDTO>> responseMap = new HashMap<>();

        List<String> chatRoomUserList = chatService.chatRoomUserList(chatRoomId, userId);

        List<UserDTO> chatRoomUser = new ArrayList<>();

        for (String user : chatRoomUserList) {
            chatRoomUser.add(UserDTO.builder().nickname(user).build());
        }
        List<UserDTO> friends = friendService.getFriends(userId).stream()
                .filter(friend -> !chatRoomUserList.contains(friend.getNickname()))
                .collect(Collectors.toList());

        responseMap.put("roomMembers", chatRoomUser);
        responseMap.put("friends", friends);

        return responseMap;
    }

    // 채팅 방에 유저를 초대
    @PostMapping("/room/invite")
    public ResponseEntity<String> inviteUser(@RequestBody InviteDTO inviteDTO) {

        List<UserDTO> friends = inviteDTO.getUsers();
        Long chatRoomId = inviteDTO.getChatRoomId();

        for (UserDTO friend : friends) {
            chatService.inviteUser(chatRoomId, friend.getUserId());
        }

        return ResponseEntity.ok().build();
    }

    // 채팅 방 이름 변경
    @PutMapping("/room/rename")
    public ResponseEntity<String> renameChatRoom(@RequestParam("chatRoomId") Long chatRoomId, @RequestParam("chatRoomName") String chatRoomName) {

        ChatRoom chatRoom = chatService.getChatRoomById(chatRoomId);
        chatRoom.setChatRoomName(chatRoomName);
        chatService.saveChatRoom(chatRoom);

        return ResponseEntity.ok().build();
    }

    // 채팅 방 나감
    @DeleteMapping("/room/quit")
    public ResponseEntity<String> quitChatRoom(@RequestParam("chatRoomId") Long chatRoomId, @RequestParam("userId") Long userId) {

        chatService.quitChatRoom(chatRoomId, userId);
        return ResponseEntity.ok().build();
    }

    // 메시지를 DB에 저장
    @PostMapping("/message/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO messageDTO) {

        Message message = new Message();

        message.setChatRoom(chatService.getChatRoomById(messageDTO.getChatRoomId()));
        message.setMessageSender(userService.getUserById(messageDTO.getMessageSenderId()));
        message.setMessageContent(messageDTO.getMessageContent());

        ChatRoom chatRoom = message.getChatRoom();
        chatRoom.setChatRoomUpdatedAt(LocalDateTime.now());

        chatService.saveChatRoom(chatRoom);
        chatService.saveMessage(message);

        return ResponseEntity.ok().build();
    }

    // 채팅 방의 메시지 목록을 받아옴
    @GetMapping("/message/list")
    public List<MessageDTO> getMessages(@RequestParam("chatRoomId") Long chatRoomId) {

        List<Message> messageList = chatService.getMessagesByChatRoomId(chatRoomId);
        List<MessageDTO> messageDTOList = new ArrayList<>();

        for (Message message : messageList) {
            messageDTOList.add(
                    MessageDTO.builder()
                            .messageId(message.getMessageId())
                            .messageSenderId(message.getMessageSender().getUserId())
                            .messageSenderNickname(message.getMessageSender().getNickname())
                            .chatRoomId(message.getChatRoom().getChatRoomId())
                            .messageContent(message.getMessageContent())
                            .messageTimestamp(message.getMessageTimestamp())
                            .build()
            );
        }

        return messageDTOList;
    }

    // 특정 메시지를 특정 유저가 읽음으로 처리
    @PutMapping("/message/read")
    public ResponseEntity<String> readMessage(@RequestParam("messageId") Long messageId, @RequestParam("userId") Long userId) {

        chatService.markMessageAsRead(messageId, userId);

        return ResponseEntity.ok().build();
    }

    // 특정 방의 모든 메시지를 특정 유저가 읽음으로 처리
    @PutMapping("/message/read/all")
    public ResponseEntity<String> readAllMessages(@RequestParam("roomId") Long roomId, @RequestParam("userId") Long userId) {

        List<Long> readStatusIds = chatService.getUnreadMessageIds(roomId, userId);

        for (Long readStatusId : readStatusIds) {
            chatService.updateReadStatusTrue(readStatusId);
        }

        return ResponseEntity.ok().build();
    }
}
