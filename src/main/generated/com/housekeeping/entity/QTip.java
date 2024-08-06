package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTip is a Querydsl query type for Tip
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTip extends EntityPathBase<Tip> {

    private static final long serialVersionUID = -531895820L;

    public static final QTip tip = new QTip("tip");

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final EnumPath<com.housekeeping.entity.enums.TipCategory> tipCategory = createEnum("tipCategory", com.housekeeping.entity.enums.TipCategory.class);

    public final StringPath tipContent = createString("tipContent");

    public final DateTimePath<java.time.LocalDateTime> tipCreatedDate = createDateTime("tipCreatedDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> tipId = createNumber("tipId", Long.class);

    public final StringPath tipTitle = createString("tipTitle");

    public final NumberPath<Integer> tipViews = createNumber("tipViews", Integer.class);

    public QTip(String variable) {
        super(Tip.class, forVariable(variable));
    }

    public QTip(Path<? extends Tip> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTip(PathMetadata metadata) {
        super(Tip.class, metadata);
    }

}

