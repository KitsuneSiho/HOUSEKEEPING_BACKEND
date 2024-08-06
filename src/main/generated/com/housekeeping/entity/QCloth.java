package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCloth is a Querydsl query type for Cloth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCloth extends EntityPathBase<Cloth> {

    private static final long serialVersionUID = -66382541L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCloth cloth = new QCloth("cloth");

    public final StringPath clothColor = createString("clothColor");

    public final StringPath clothCustomTag = createString("clothCustomTag");

    public final NumberPath<Long> clothId = createNumber("clothId", Long.class);

    public final StringPath clothMaterial = createString("clothMaterial");

    public final StringPath clothName = createString("clothName");

    public final EnumPath<com.housekeeping.entity.enums.ClothSeason> clothSeason = createEnum("clothSeason", com.housekeeping.entity.enums.ClothSeason.class);

    public final StringPath clothType = createString("clothType");

    public final StringPath imageUrl = createString("imageUrl");

    public final QUser user;

    public QCloth(String variable) {
        this(Cloth.class, forVariable(variable), INITS);
    }

    public QCloth(Path<? extends Cloth> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCloth(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCloth(PathMetadata metadata, PathInits inits) {
        this(Cloth.class, metadata, inits);
    }

    public QCloth(Class<? extends Cloth> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

