package com.housekeeping.controller;

import com.housekeeping.entity.Room;
import com.housekeeping.entity.Schedule;
import com.housekeeping.service.RoomService;
import com.housekeeping.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final ScheduleService scheduleService;
    private final RoomService roomService;

    // 방 정보 주기
    @PostMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms(@RequestBody List<Long> roomIds) {
        if (roomIds == null || roomIds.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Room> rooms = roomService.getRoomsByIds(roomIds);
        return ResponseEntity.ok(rooms);
    }

    // 스케줄 리스트
    @PostMapping("/view")
    public ResponseEntity<?> getUserSchedules(@RequestBody List<Long> roomIds) {
        if (roomIds == null || roomIds.isEmpty()) {
            return ResponseEntity.badRequest().body("방이없다");
        }

        Map<Long, List<Schedule>> schedules = scheduleService.getSchedulesByRoomIds(roomIds);
        return ResponseEntity.ok(schedules);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSchedule(@RequestBody Schedule newSchedule) {
        Schedule addedSchedule = scheduleService.addSchedule(newSchedule);
        return ResponseEntity.ok(addedSchedule);
    }

    // 스케줄 이름만 수정
    @PatchMapping("/updateName/{id}")
    public ResponseEntity<?> updateScheduleName(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String scheduleName = payload.get("scheduleName");
        return ResponseEntity.ok(scheduleService.updateScheduleName(id, scheduleName));
    }

    // 스케줄 체크 여부 수정
    @PatchMapping("/updateChecked/{scheduleId}")
    public ResponseEntity<Schedule> updateChecked(@PathVariable Long scheduleId, @RequestBody Map<String, Boolean> body) {
        Boolean checked = body.get("checked");
        if (checked == null) {
            return ResponseEntity.badRequest().build();
        }
        Schedule updatedSchedule = scheduleService.updateScheduleChecked(scheduleId, checked);
        return ResponseEntity.ok(updatedSchedule);
    }

    // 스케줄 삭제
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
