package com.housekeeping.repository.custom;

import com.housekeeping.entity.Message;
import com.querydsl.core.Tuple;

import java.util.List;

public interface MessageRepositoryCustom {

    // 채팅 방에 있는 모든 메시지를 리스트로 반환
    List<Message> getMessagesByChatRoomId(Long chatRoomId);
    // 채팅 방에서 가장 최근에 보내진 메시지의 발신자, 내용, 시간을 반환
    Tuple getRecentMessageByChatRoomId(Long chatRoomId);
}
