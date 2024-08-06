package com.housekeeping.service;

import com.housekeeping.DTO.ChatRoomDTO;
import com.housekeeping.DTO.MessageDTO;
import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.ChatRoom;
import com.housekeeping.entity.ChatRoomMember;
import com.housekeeping.entity.Message;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Transactional
public interface ChatService {

    // 채팅 방 번호를 이용해서 채팅 방 정보를 반환
    ChatRoom getChatRoomById(Long ChatRoomId);
    // 특정 회원이 들어가 있는 모든 채팅 방 정보를 반환
    List<ChatRoomDTO> getChatRoomsByUserId(Long userId);
    // 특정 회원을 제외하고 채팅 방에 들어있는 유저들의 닉네임을 리스트로 반환
    List<String> findUserNicknamesByChatRoomId(Long chatRoomId, Long userId);
    // 채팅 방 생성/업데이트
    ChatRoom saveChatRoom(ChatRoom chatRoom);
    // 채팅 방에 회원을 초대
    ChatRoomMember inviteUser(Long chatRoomId, Long userId);
    // 채팅 방에서 특정 유저를 내보냄
    void quitChatRoom(Long chatRoomId, Long userId);
    // 특정 채팅 방의 모든 메시지를 반환
    List<Message> getMessagesByChatRoomId(Long chatRoomId);
    // 채팅 방의 가장 최근 메시지를 반환
    MessageDTO getRecentMessageByChatRoomId(Long chatRoomId);
    // 메시지를 저장/업데이트
    Message saveMessage(Message message);
    // 특정 메시지를 특정 유저가 읽음 처리
    void markMessageAsRead(Long messageId, Long userId);
    // 특정 유저가 특정 채팅 방의 읽지 않은 메시지 수를 반환
    Long getUnreadMessageCount(Long chatRoomId, Long userId);
    // 특정 유저가 특정 채팅 방의 읽지 않은 메시지 번호 리스트를 반환
    List<Long> getUnreadMessageIds(Long chatRoomId, Long userId);
    // 메시지 읽음 상태 번호를 기준으로 읽음 상태로 업데이트
    void updateReadStatusTrue(Long readStatusId);
    // 채팅 방에 있는 사람들의 목록과 채팅 방에 없는 내 친구들을 반환
    List<String> chatRoomUserList(Long chatRoomId, Long userId);
}
