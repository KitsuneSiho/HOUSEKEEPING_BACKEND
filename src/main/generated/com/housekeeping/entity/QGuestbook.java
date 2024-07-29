package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGuestbook is a Querydsl query type for Guestbook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGuestbook extends EntityPathBase<Guestbook> {

    private static final long serialVersionUID = 258457434L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGuestbook guestbook = new QGuestbook("guestbook");

    public final StringPath guestbookContent = createString("guestbookContent");

    public final NumberPath<Long> guestbookId = createNumber("guestbookId", Long.class);

    public final BooleanPath guestbookIsRead = createBoolean("guestbookIsRead");

    public final BooleanPath guestbookIsSecret = createBoolean("guestbookIsSecret");

    public final com.housekeeping.entity.user.QUserEntity guestbookOwner;

    public final DateTimePath<java.time.LocalDateTime> guestbookTimestamp = createDateTime("guestbookTimestamp", java.time.LocalDateTime.class);

    public final com.housekeeping.entity.user.QUserEntity guestbookWriter;

    public QGuestbook(String variable) {
        this(Guestbook.class, forVariable(variable), INITS);
    }

    public QGuestbook(Path<? extends Guestbook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGuestbook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGuestbook(PathMetadata metadata, PathInits inits) {
        this(Guestbook.class, metadata, inits);
    }

    public QGuestbook(Class<? extends Guestbook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.guestbookOwner = inits.isInitialized("guestbookOwner") ? new com.housekeeping.entity.user.QUserEntity(forProperty("guestbookOwner")) : null;
        this.guestbookWriter = inits.isInitialized("guestbookWriter") ? new com.housekeeping.entity.user.QUserEntity(forProperty("guestbookWriter")) : null;
    }

}

