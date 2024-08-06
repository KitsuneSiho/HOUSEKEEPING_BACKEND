package com.housekeeping.repository.implement;

import com.housekeeping.entity.FriendRequest;
import com.housekeeping.entity.QFriendRequest;
import com.housekeeping.repository.custom.FriendRequestRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FriendRequestRepositoryImpl implements FriendRequestRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<FriendRequest> findByUsers(Long userId1, Long userId2) {
        QFriendRequest qFriendRequest = QFriendRequest.friendRequest;

        BooleanExpression condition = (qFriendRequest.requestSender.userId.eq(userId1).and(qFriendRequest.requestReceiver.userId.eq(userId2)))
                .or(qFriendRequest.requestSender.userId.eq(userId2).and(qFriendRequest.requestReceiver.userId.eq(userId1)));

        FriendRequest result = jpaQueryFactory.selectFrom(qFriendRequest)
                .where(condition)
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
