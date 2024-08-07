package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFurniture is a Querydsl query type for Furniture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFurniture extends EntityPathBase<Furniture> {

    private static final long serialVersionUID = 565795467L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFurniture furniture = new QFurniture("furniture");

    public final NumberPath<Long> furnitureId = createNumber("furnitureId", Long.class);

    public final StringPath furnitureName = createString("furnitureName");

    public final ListPath<FurniturePlacement, QFurniturePlacement> furniturePlacements = this.<FurniturePlacement, QFurniturePlacement>createList("furniturePlacements", FurniturePlacement.class, QFurniturePlacement.class, PathInits.DIRECT2);

    public final EnumPath<com.housekeeping.entity.enums.FurnitureType> furnitureType = createEnum("furnitureType", com.housekeeping.entity.enums.FurnitureType.class);

    public final StringPath furnitureTypeName = createString("furnitureTypeName");

    public final QLevelEXPTable level;

    public QFurniture(String variable) {
        this(Furniture.class, forVariable(variable), INITS);
    }

    public QFurniture(Path<? extends Furniture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFurniture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFurniture(PathMetadata metadata, PathInits inits) {
        this(Furniture.class, metadata, inits);
    }

    public QFurniture(Class<? extends Furniture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.level = inits.isInitialized("level") ? new QLevelEXPTable(forProperty("level")) : null;
    }

}

