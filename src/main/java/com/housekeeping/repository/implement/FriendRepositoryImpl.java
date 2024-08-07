package com.housekeeping.repository.implement;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.Friend;
import com.housekeeping.entity.QFriend;
import com.housekeeping.entity.QUser;
import com.housekeeping.repository.custom.FriendRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Tuple> findFriendsByUserId1(Long userId) {

        QFriend qFriend = QFriend.friend;

        return jpaQueryFactory.select(qFriend.friendUser1.userId, qFriend.friendUser1.nickname)
                .from(qFriend)
                .where(qFriend.friendUser2.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<Tuple> findFriendsByUserId2(Long userId) {

        QFriend qFriend = QFriend.friend;

        return jpaQueryFactory.select(qFriend.friendUser2.userId, qFriend.friendUser2.nickname)
                .from(qFriend)
                .where(qFriend.friendUser1.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<Tuple> fineOnlineFriends1(Long userId) {

        QFriend qFriend = QFriend.friend;

        return jpaQueryFactory.select(qFriend.friendUser1.userId, qFriend.friendUser1.nickname)
                .from(qFriend)
                .where(qFriend.friendUser2.userId.eq(userId).and(qFriend.friendUser1.userIsOnline.eq(true)))
                .fetch();
    }

    @Override
    public List<Tuple> fineOnlineFriends2(Long userId) {

        QFriend qFriend = QFriend.friend;

        return jpaQueryFactory.select(qFriend.friendUser2.userId, qFriend.friendUser2.nickname)
                .from(qFriend)
                .where(qFriend.friendUser1.userId.eq(userId).and(qFriend.friendUser2.userIsOnline.eq(true)))
                .fetch();
    }

    @Override
    public Optional<Friend> findByUsers(Long userId1, Long userId2) {
        QFriend qFriend = QFriend.friend;

        BooleanExpression condition = (qFriend.friendUser1.userId.eq(userId1).and(qFriend.friendUser2.userId.eq(userId2)))
                .or(qFriend.friendUser1.userId.eq(userId2).and(qFriend.friendUser2.userId.eq(userId1)));

        Friend result = jpaQueryFactory.selectFrom(qFriend)
                .where(condition)
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
