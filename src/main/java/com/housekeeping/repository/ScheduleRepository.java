package com.housekeeping.repository;

import com.housekeeping.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 특정 날짜와 여러 roomId로 스케줄을 조회하는 메서드
    List<Schedule> findByScheduleDateAndRoomIdIn(LocalDateTime scheduleDate, List<Long> roomIds);
}
