package com.housekeeping.repository.implement;


import com.housekeeping.entity.*;
import com.housekeeping.repository.custom.ChatRoomMemberRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberRepositoryImpl implements ChatRoomMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QChatRoomMember qChatRoomMember = QChatRoomMember.chatRoomMember;
    QUser qUser = QUser.user;

    @Override
    public List<ChatRoom> findChatRoomsByUserId(Long userId) {

        return queryFactory.select(qChatRoomMember.chatRoom)
                .from(qChatRoomMember)
                .where(qChatRoomMember.user.userId.eq(userId))
                .orderBy(qChatRoomMember.chatRoom.chatRoomUpdatedAt.desc())
                .fetch();
    }

    @Override
    public ChatRoomMember findChatRoomMemberByChatRoomIdAndUserId(Long chatRoomId, Long userId) {

        return queryFactory.selectFrom(qChatRoomMember)
                .where(qChatRoomMember.chatRoom.chatRoomId.eq(chatRoomId)
                        .and(qChatRoomMember.user.userId.eq(userId)))
                .fetchOne();
    }

    @Override
    public List<String> findUserNicknamesByChatRoomId(Long chatRoomId, Long userId) {

        return queryFactory
                .select(qUser.nickname)
                .from(qChatRoomMember)
                .join(qChatRoomMember.user, qUser)
                .where(qChatRoomMember.chatRoom.chatRoomId.eq(chatRoomId).and(qUser.userId.ne(userId)))
                .fetch();
    }

    @Override
    public List<User> findUsersByChatRoomId(Long chatRoomId) {

        return queryFactory
                .select(qUser)
                .from(qChatRoomMember)
                .join(qChatRoomMember.user, qUser)
                .where(qChatRoomMember.chatRoom.chatRoomId.eq(chatRoomId))
                .fetch();
    }
}
