package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoomFurnitureCategory is a Querydsl query type for RoomFurnitureCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomFurnitureCategory extends EntityPathBase<RoomFurnitureCategory> {

    private static final long serialVersionUID = -1201177458L;

    public static final QRoomFurnitureCategory roomFurnitureCategory = new QRoomFurnitureCategory("roomFurnitureCategory");

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final EnumPath<com.housekeeping.entity.enums.FurnitureType> furnitureType = createEnum("furnitureType", com.housekeeping.entity.enums.FurnitureType.class);

    public final EnumPath<com.housekeeping.entity.enums.RoomType> roomType = createEnum("roomType", com.housekeeping.entity.enums.RoomType.class);

    public final StringPath typeName = createString("typeName");

    public QRoomFurnitureCategory(String variable) {
        super(RoomFurnitureCategory.class, forVariable(variable));
    }

    public QRoomFurnitureCategory(Path<? extends RoomFurnitureCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoomFurnitureCategory(PathMetadata metadata) {
        super(RoomFurnitureCategory.class, metadata);
    }

}

