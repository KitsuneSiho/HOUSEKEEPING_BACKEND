package com.housekeeping.repository;

import com.housekeeping.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByRoom_RoomId(Long roomId);
    // 특정 루틴 ID를 가진 모든 스케줄 조회
    List<Schedule> findByRoutine_RoutineId(Long routineId);
    // 특정 루틴 ID와 날짜 범위에 해당하는 모든 스케줄 삭제
    void deleteByRoutine_RoutineIdAndScheduleDateBetween(Long routineId, LocalDateTime startDate, LocalDateTime endDate);
}
