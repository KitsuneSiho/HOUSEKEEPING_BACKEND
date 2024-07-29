package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = 691045026L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoom room = new QRoom("room");

    public final ListPath<FurniturePlacement, QFurniturePlacement> furniturePlacements = this.<FurniturePlacement, QFurniturePlacement>createList("furniturePlacements", FurniturePlacement.class, QFurniturePlacement.class, PathInits.DIRECT2);

    public final NumberPath<Long> roomId = createNumber("roomId", Long.class);

    public final StringPath roomName = createString("roomName");

    public final NumberPath<Integer> roomPollution = createNumber("roomPollution", Integer.class);

    public final EnumPath<com.housekeeping.entity.enums.RoomType> roomType = createEnum("roomType", com.housekeeping.entity.enums.RoomType.class);

    public final ListPath<Routine, QRoutine> routines = this.<Routine, QRoutine>createList("routines", Routine.class, QRoutine.class, PathInits.DIRECT2);

    public final ListPath<Schedule, QSchedule> schedules = this.<Schedule, QSchedule>createList("schedules", Schedule.class, QSchedule.class, PathInits.DIRECT2);

    public final com.housekeeping.entity.user.QUser user;

    public QRoom(String variable) {
        this(Room.class, forVariable(variable), INITS);
    }

    public QRoom(Path<? extends Room> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoom(PathMetadata metadata, PathInits inits) {
        this(Room.class, metadata, inits);
    }

    public QRoom(Class<? extends Room> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.housekeeping.entity.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

