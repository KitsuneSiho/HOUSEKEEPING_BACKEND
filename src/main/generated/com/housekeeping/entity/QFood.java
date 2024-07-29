package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFood is a Querydsl query type for Food
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFood extends EntityPathBase<Food> {

    private static final long serialVersionUID = 690687525L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFood food = new QFood("food");

    public final EnumPath<com.housekeeping.entity.enums.FoodCategory> foodCategory = createEnum("foodCategory", com.housekeeping.entity.enums.FoodCategory.class);

    public final DateTimePath<java.time.LocalDateTime> foodExpirationDate = createDateTime("foodExpirationDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> foodId = createNumber("foodId", Long.class);

    public final StringPath foodMemo = createString("foodMemo");

    public final StringPath foodName = createString("foodName");

    public final NumberPath<Integer> foodQuantity = createNumber("foodQuantity", Integer.class);

    public final com.housekeeping.entity.user.QUser user;

    public QFood(String variable) {
        this(Food.class, forVariable(variable), INITS);
    }

    public QFood(Path<? extends Food> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFood(PathMetadata metadata, PathInits inits) {
        this(Food.class, metadata, inits);
    }

    public QFood(Class<? extends Food> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.housekeeping.entity.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

