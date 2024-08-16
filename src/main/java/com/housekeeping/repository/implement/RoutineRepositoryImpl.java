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

    @Override
    public String findDistinctCheckedRoutineGroupNamesByUserId(Long userId) {
        return queryFactory
                .selectDistinct(routine.routineGroupName) // DISTINCT로 중복 제거
                .from(routine)
                .join(routine.room, room) // 루틴과 사용자 엔티티를 join
                .where(room.user.userId.eq(userId) // 특정 사용자 ID에 대한 필터링
                        .and(routine.routineIsChecked.isTrue()))
                .fetchFirst();// 체크된 루틴만 필터링

    }
}