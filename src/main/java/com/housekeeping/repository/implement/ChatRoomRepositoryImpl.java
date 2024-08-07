package com.housekeeping.repository.implement;

import com.housekeeping.entity.QChatRoom;
import com.housekeeping.entity.QChatRoomMember;
import com.housekeeping.entity.enums.ChatRoomType;
import com.housekeeping.repository.custom.ChatRoomRepositoryCustom;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPQLQueryFactory queryFactory;
    private final QChatRoom qChatRoom = QChatRoom.chatRoom;
    private final QChatRoomMember qChatRoomMember = QChatRoomMember.chatRoomMember;


    @Override
    public Long findChatRoomIdByChatRoomIdAndUserId(Long chatRoomId, Long userId) {

        return queryFactory.select(qChatRoom.chatRoomId)
                .from(qChatRoom)
                .join(qChatRoomMember)
                .on(qChatRoom.chatRoomId.eq(qChatRoomMember.chatRoom.chatRoomId))
                .where(qChatRoom.chatRoomId.eq(chatRoomId)
                        .and(qChatRoomMember.user.userId.eq(userId)))
                .fetchOne();
    }

    @Override
    public List<Long> findChatRoomIdsByUserId(Long userId) {

        return queryFactory.select(qChatRoom.chatRoomId)
                .from(qChatRoom)
                .join(qChatRoomMember)
                .on(qChatRoom.chatRoomId.eq(qChatRoomMember.chatRoom.chatRoomId))
                .where(qChatRoom.chatRoomType.eq(ChatRoomType.SINGLE)
                        .and(qChatRoomMember.user.userId.eq(userId)))
                .fetch();
    }
}
