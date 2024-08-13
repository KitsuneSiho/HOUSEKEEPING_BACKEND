package com.housekeeping.repository.implement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.housekeeping.repository.custom.RoutineRepositoryCustom;
import java.util.List;

import static com.housekeeping.entity.QRoutine.routine;
import static com.housekeeping.entity.QRoom.room;

@Repository
@RequiredArgsConstructor
public class RoutineRepositoryImpl implements RoutineRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findDistinctRoutineGroupNamesByUserId(Long userId) {
        return queryFactory
                .select(routine.routineGroupName)
                .distinct()
                .from(routine)
                .join(routine.room, room)
                .where(room.user.userId.eq(userId))
                .fetch();
    }
}