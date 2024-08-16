package com.housekeeping.repository.implement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.housekeeping.repository.custom.RoutineRepositoryCustom;
import java.util.List;

import static com.housekeeping.entity.QRoutine.routine;
import static com.housekeeping.entity.QRoom.room;
import static com.housekeeping.entity.QSchedule.schedule;

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

    @Override
    public void toggleRoomAlarms(Long roomId, String routineGroupName) {
        // 1. 해당 roomId와 routineGroupName에 해당하는 모든 루틴의 알람을 켬
        queryFactory
                .update(routine)
                .set(routine.routineIsAlarm, true)
                .where(routine.room.roomId.eq(roomId)
                        .and(routine.routineGroupName.eq(routineGroupName)))
                .execute();

        // 2. 해당 루틴과 관련된 모든 스케줄의 알람도 켬
        queryFactory
                .update(schedule)
                .set(schedule.scheduleIsAlarm, true)
                .where(schedule.routine.room.roomId.eq(roomId)
                        .and(schedule.routine.routineGroupName.eq(routineGroupName)))
                .execute();
    }
}