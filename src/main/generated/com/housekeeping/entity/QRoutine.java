package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoutine is a Querydsl query type for Routine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoutine extends EntityPathBase<Routine> {

    private static final long serialVersionUID = 1149973917L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoutine routine = new QRoutine("routine");

    public final QRoom room;

    public final EnumPath<com.housekeeping.entity.enums.RoutineFrequency> routineFrequency = createEnum("routineFrequency", com.housekeeping.entity.enums.RoutineFrequency.class);

    public final StringPath routineGroupName = createString("routineGroupName");

    public final NumberPath<Long> routineId = createNumber("routineId", Long.class);

    public final StringPath routineInterval = createString("routineInterval");

    public final BooleanPath routineIsAlarm = createBoolean("routineIsAlarm");

    public final BooleanPath routineIsChecked = createBoolean("routineIsChecked");

    public final StringPath routineName = createString("routineName");

    public final ListPath<Schedule, QSchedule> schedules = this.<Schedule, QSchedule>createList("schedules", Schedule.class, QSchedule.class, PathInits.DIRECT2);

    public QRoutine(String variable) {
        this(Routine.class, forVariable(variable), INITS);
    }

    public QRoutine(Path<? extends Routine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoutine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoutine(PathMetadata metadata, PathInits inits) {
        this(Routine.class, metadata, inits);
    }

    public QRoutine(Class<? extends Routine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
    }

}

