package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessageReadStatus is a Querydsl query type for MessageReadStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessageReadStatus extends EntityPathBase<MessageReadStatus> {

    private static final long serialVersionUID = -1300486616L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessageReadStatus messageReadStatus = new QMessageReadStatus("messageReadStatus");

    public final BooleanPath isRead = createBoolean("isRead");

    public final QMessage message;

    public final NumberPath<Long> readStatusId = createNumber("readStatusId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> readTimestamp = createDateTime("readTimestamp", java.time.LocalDateTime.class);

    public final com.housekeeping.entity.user.QUser user;

    public QMessageReadStatus(String variable) {
        this(MessageReadStatus.class, forVariable(variable), INITS);
    }

    public QMessageReadStatus(Path<? extends MessageReadStatus> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessageReadStatus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessageReadStatus(PathMetadata metadata, PathInits inits) {
        this(MessageReadStatus.class, metadata, inits);
    }

    public QMessageReadStatus(Class<? extends MessageReadStatus> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.message = inits.isInitialized("message") ? new QMessage(forProperty("message"), inits.get("message")) : null;
        this.user = inits.isInitialized("user") ? new com.housekeeping.entity.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

