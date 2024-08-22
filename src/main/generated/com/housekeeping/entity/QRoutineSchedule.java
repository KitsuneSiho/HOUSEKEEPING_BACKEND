package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoutineSchedule is a Querydsl query type for RoutineSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoutineSchedule extends EntityPathBase<RoutineSchedule> {

    private static final long serialVersionUID = 2092908372L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoutineSchedule routineSchedule = new QRoutineSchedule("routineSchedule");

    public final QRoom room;

    public final QRoutine routine;

    public final DateTimePath<java.time.LocalDateTime> scheduleDate = createDateTime("scheduleDate", java.time.LocalDateTime.class);

    public final StringPath scheduleDetail = createString("scheduleDetail");

    public final NumberPath<Long> scheduleId = createNumber("scheduleId", Long.class);

    public final BooleanPath scheduleIsAlarm = createBoolean("scheduleIsAlarm");

    public final BooleanPath scheduleIsChecked = createBoolean("scheduleIsChecked");

    public final StringPath scheduleName = createString("scheduleName");

    public QRoutineSchedule(String variable) {
        this(RoutineSchedule.class, forVariable(variable), INITS);
    }

    public QRoutineSchedule(Path<? extends RoutineSchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoutineSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoutineSchedule(PathMetadata metadata, PathInits inits) {
        this(RoutineSchedule.class, metadata, inits);
    }

    public QRoutineSchedule(Class<? extends RoutineSchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
        this.routine = inits.isInitialized("routine") ? new QRoutine(forProperty("routine"), inits.get("routine")) : null;
    }

}

