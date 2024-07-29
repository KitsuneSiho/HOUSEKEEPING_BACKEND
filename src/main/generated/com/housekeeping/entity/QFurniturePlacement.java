package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFurniturePlacement is a Querydsl query type for FurniturePlacement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFurniturePlacement extends EntityPathBase<FurniturePlacement> {

    private static final long serialVersionUID = 375162010L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFurniturePlacement furniturePlacement = new QFurniturePlacement("furniturePlacement");

    public final QFurniture furniture;

    public final NumberPath<Long> placementId = createNumber("placementId", Long.class);

    public final StringPath placementLocation = createString("placementLocation");

    public final QRoom room;

    public QFurniturePlacement(String variable) {
        this(FurniturePlacement.class, forVariable(variable), INITS);
    }

    public QFurniturePlacement(Path<? extends FurniturePlacement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFurniturePlacement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFurniturePlacement(PathMetadata metadata, PathInits inits) {
        this(FurniturePlacement.class, metadata, inits);
    }

    public QFurniturePlacement(Class<? extends FurniturePlacement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.furniture = inits.isInitialized("furniture") ? new QFurniture(forProperty("furniture"), inits.get("furniture")) : null;
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
    }

}

