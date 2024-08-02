package com.housekeeping.repository.implement;

import com.housekeeping.entity.Message;
import com.housekeeping.entity.QMessage;
import com.housekeeping.repository.custom.MessageRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Message> getMessagesByChatRoomId(Long chatRoomId) {

        QMessage qMessage = QMessage.message;

        return queryFactory.select(qMessage)
                .from(qMessage)
                .where(qMessage.chatRoom.chatRoomId.eq(chatRoomId))
                .fetch();
    }

    @Override
    public Tuple getRecentMessageByChatRoomId(Long chatRoomId) {

        QMessage qMessage = QMessage.message;

        return queryFactory.select(qMessage.messageId, qMessage.messageContent, qMessage.messageTimestamp)
                .from(qMessage)
                .where(qMessage.chatRoom.chatRoomId.eq(chatRoomId))
                .orderBy(qMessage.messageTimestamp.desc())
                .fetchFirst();
    }
}
