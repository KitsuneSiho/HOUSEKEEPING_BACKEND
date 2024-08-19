package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecommendRoutine is a Querydsl query type for RecommendRoutine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendRoutine extends EntityPathBase<RecommendRoutine> {

    private static final long serialVersionUID = 698379183L;

    public static final QRecommendRoutine recommendRoutine = new QRecommendRoutine("recommendRoutine");

    public final NumberPath<Long> recommendRoutineId = createNumber("recommendRoutineId", Long.class);

    public final NumberPath<Long> roomNo = createNumber("roomNo", Long.class);

    public final EnumPath<com.housekeeping.entity.enums.RoutineFrequency> routineFrequency = createEnum("routineFrequency", com.housekeeping.entity.enums.RoutineFrequency.class);

    public final StringPath routineName = createString("routineName");

    public QRecommendRoutine(String variable) {
        super(RecommendRoutine.class, forVariable(variable));
    }

    public QRecommendRoutine(Path<? extends RecommendRoutine> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecommendRoutine(PathMetadata metadata) {
        super(RecommendRoutine.class, metadata);
    }

}

