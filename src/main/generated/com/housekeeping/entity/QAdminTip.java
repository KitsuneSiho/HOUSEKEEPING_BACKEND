package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdminTip is a Querydsl query type for AdminTip
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminTip extends EntityPathBase<AdminTip> {

    private static final long serialVersionUID = 314774803L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdminTip adminTip = new QAdminTip("adminTip");

    public final QUser author;

    public final EnumPath<com.housekeeping.entity.enums.TipCategory> category = createEnum("category", com.housekeeping.entity.enums.TipCategory.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QAdminTip(String variable) {
        this(AdminTip.class, forVariable(variable), INITS);
    }

    public QAdminTip(Path<? extends AdminTip> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdminTip(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdminTip(PathMetadata metadata, PathInits inits) {
        this(AdminTip.class, metadata, inits);
    }

    public QAdminTip(Class<? extends AdminTip> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new QUser(forProperty("author"), inits.get("author")) : null;
    }

}

