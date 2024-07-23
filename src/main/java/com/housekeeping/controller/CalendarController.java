package com.housekeeping.controller;

import com.housekeeping.entity.Schedule;
import com.housekeeping.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final ScheduleService scheduleService;

    // 특정 날짜의 사용자 roomId에 해당하는 스케줄 보기
    @GetMapping("/view")
    public ResponseEntity<List<Schedule>> calendarView(
            @RequestParam String date,
            @RequestParam List<Long> roomIds) {

        // 날짜 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDateTime dateTime = LocalDateTime.parse(date + "T00:00:00", formatter);

        // 스케줄 조회
        List<Schedule> schedules = scheduleService.getSchedulesByDateAndRooms(dateTime, roomIds);
        return ResponseEntity.ok(schedules);
    }


    // 특정 날짜의 스케줄 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<?> calenderUpdate(@PathVariable Long id, @RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, schedule));
    }

    // 특정 날짜의 스케줄 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> calenderDelete(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    // 특정 날짜의 스케줄 알림 켜기, 끄기
    @PutMapping("/alarm/{id}")
    public ResponseEntity<?> calenderAlarm(@PathVariable Long id, @RequestParam boolean isChecked) {
        return ResponseEntity.ok(scheduleService.toggleScheduleAlarm(id, isChecked));
    }
}
