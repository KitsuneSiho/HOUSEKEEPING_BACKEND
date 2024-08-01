package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFriendRequest is a Querydsl query type for FriendRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFriendRequest extends EntityPathBase<FriendRequest> {

    private static final long serialVersionUID = -974585814L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFriendRequest friendRequest = new QFriendRequest("friendRequest");

    public final DateTimePath<java.time.LocalDateTime> requestDate = createDateTime("requestDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> requestId = createNumber("requestId", Long.class);

    public final com.housekeeping.entity.user.QUser requestReceiver;

    public final com.housekeeping.entity.user.QUser requestSender;

    public final EnumPath<com.housekeeping.entity.enums.RequestStatus> requestStatus = createEnum("requestStatus", com.housekeeping.entity.enums.RequestStatus.class);

    public QFriendRequest(String variable) {
        this(FriendRequest.class, forVariable(variable), INITS);
    }

    public QFriendRequest(Path<? extends FriendRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFriendRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFriendRequest(PathMetadata metadata, PathInits inits) {
        this(FriendRequest.class, metadata, inits);
    }

    public QFriendRequest(Class<? extends FriendRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.requestReceiver = inits.isInitialized("requestReceiver") ? new com.housekeeping.entity.user.QUser(forProperty("requestReceiver"), inits.get("requestReceiver")) : null;
        this.requestSender = inits.isInitialized("requestSender") ? new com.housekeeping.entity.user.QUser(forProperty("requestSender"), inits.get("requestSender")) : null;
    }

}

