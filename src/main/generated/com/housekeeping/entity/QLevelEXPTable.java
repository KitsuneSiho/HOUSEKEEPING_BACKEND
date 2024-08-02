package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLevelEXPTable is a Querydsl query type for LevelEXPTable
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLevelEXPTable extends EntityPathBase<LevelEXPTable> {

    private static final long serialVersionUID = -474785426L;

    public static final QLevelEXPTable levelEXPTable = new QLevelEXPTable("levelEXPTable");

    public final ListPath<Furniture, QFurniture> furniture = this.<Furniture, QFurniture>createList("furniture", Furniture.class, QFurniture.class, PathInits.DIRECT2);

    public final NumberPath<Long> levelId = createNumber("levelId", Long.class);

    public final NumberPath<Integer> levelLevel = createNumber("levelLevel", Integer.class);

    public final StringPath levelName = createString("levelName");

    public final NumberPath<Integer> levelRequireEXP = createNumber("levelRequireEXP", Integer.class);

    public final ListPath<com.housekeeping.entity.user.User, com.housekeeping.entity.user.QUser> users = this.<com.housekeeping.entity.user.User, com.housekeeping.entity.user.QUser>createList("users", com.housekeeping.entity.user.User.class, com.housekeeping.entity.user.QUser.class, PathInits.DIRECT2);

    public QLevelEXPTable(String variable) {
        super(LevelEXPTable.class, forVariable(variable));
    }

    public QLevelEXPTable(Path<? extends LevelEXPTable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLevelEXPTable(PathMetadata metadata) {
        super(LevelEXPTable.class, metadata);
    }

}

