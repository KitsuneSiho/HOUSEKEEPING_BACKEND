package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFriend is a Querydsl query type for Friend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFriend extends EntityPathBase<Friend> {

    private static final long serialVersionUID = -1966623067L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFriend friend = new QFriend("friend");

    public final DateTimePath<java.time.LocalDateTime> friendDate = createDateTime("friendDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> friendId = createNumber("friendId", Long.class);

    public final com.housekeeping.entity.user.QUser friendUser1;

    public final com.housekeeping.entity.user.QUser friendUser2;

    public QFriend(String variable) {
        this(Friend.class, forVariable(variable), INITS);
    }

    public QFriend(Path<? extends Friend> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFriend(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFriend(PathMetadata metadata, PathInits inits) {
        this(Friend.class, metadata, inits);
    }

    public QFriend(Class<? extends Friend> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.friendUser1 = inits.isInitialized("friendUser1") ? new com.housekeeping.entity.user.QUser(forProperty("friendUser1"), inits.get("friendUser1")) : null;
        this.friendUser2 = inits.isInitialized("friendUser2") ? new com.housekeeping.entity.user.QUser(forProperty("friendUser2"), inits.get("friendUser2")) : null;
    }

}

