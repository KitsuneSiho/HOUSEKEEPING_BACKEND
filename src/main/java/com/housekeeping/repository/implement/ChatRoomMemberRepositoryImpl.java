package com.housekeeping.repository.implement;

import com.housekeeping.entity.ChatRoom;
import com.housekeeping.entity.ChatRoomMember;
import com.housekeeping.repository.custom.ChatRoomMemberRepositoryCustom;
import com.housekeeping.entity.QChatRoomMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberRepositoryImpl implements ChatRoomMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QChatRoomMember qChatRoomMember = QChatRoomMember.chatRoomMember;

    @Override
    public List<ChatRoom> findChatRoomsByUserId(Long userId) {

        return queryFactory.select(qChatRoomMember.chatRoom)
                           .from(qChatRoomMember)
                           .where(qChatRoomMember.user.userId.eq(userId))
                           .fetch();
    }

    @Override
    public ChatRoomMember findChatRoomMemberByChatRoomIdAndUserId(Long chatRoomId, Long userId) {

        return queryFactory.selectFrom(qChatRoomMember)
            .where(qChatRoomMember.chatRoom.chatRoomId.eq(chatRoomId)
                    .and(qChatRoomMember.user.userId.eq(userId)))
            .fetchOne();
    }
}
