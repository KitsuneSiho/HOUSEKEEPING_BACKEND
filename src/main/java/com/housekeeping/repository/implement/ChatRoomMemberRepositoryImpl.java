package com.housekeeping.repository.implement;

import com.housekeeping.entity.ChatRoom;
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

    @Override
    public List<ChatRoom> findChatRoomsByUserId(Long userId) {
        QChatRoomMember qChatRoomMember = QChatRoomMember.chatRoomMember;

        return queryFactory.select(qChatRoomMember.chatRoom)
                           .from(qChatRoomMember)
                           .where(qChatRoomMember.user.userId.eq(userId))
                           .fetch();
    }
}
