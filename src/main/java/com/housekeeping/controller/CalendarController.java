package com.housekeeping.controller;

import com.housekeeping.DTO.ScheduleDTO;
import com.housekeeping.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final ScheduleService scheduleService;

    // 사용자의 방에 해당하는 스케줄 조회
    @PostMapping("/view")
    public ResponseEntity<?> getUserSchedules(@RequestBody List<Long> roomIds) {
        if (roomIds == null || roomIds.isEmpty()) {
            return ResponseEntity.badRequest().body("방이없다");
        }

        Map<Long, List<ScheduleDTO>> schedules = scheduleService.getSchedulesByRoomIds(roomIds);
        return ResponseEntity.ok(schedules);
    }

    // 스케줄 추가
    @PostMapping("/add")
    public ResponseEntity<ScheduleDTO> addSchedule(@RequestBody ScheduleDTO newScheduleDTO) {
        ScheduleDTO addedSchedule = scheduleService.addSchedule(newScheduleDTO);
        return ResponseEntity.ok(addedSchedule);
    }

    // 스케줄 이름 변경
    @PatchMapping("/updateName/{id}")
    public ResponseEntity<ScheduleDTO> updateScheduleName(@PathVariable("id") Long id, @RequestBody Map<String, String> payload) {
        String scheduleName = payload.get("scheduleName");
        ScheduleDTO updatedSchedule = scheduleService.updateScheduleName(id, scheduleName);
        return ResponseEntity.ok(updatedSchedule);
    }

    // 스케줄 체크, 미체크 반영
    @PatchMapping("/updateChecked/{scheduleId}")
    public ResponseEntity<ScheduleDTO> updateChecked(@PathVariable("scheduleId") Long scheduleId, @RequestBody Map<String, Boolean> body) {
        Boolean checked = body.get("checked");
        if (checked == null) {
            return ResponseEntity.badRequest().build();
        }
        ScheduleDTO updatedSchedule = scheduleService.updateScheduleChecked(scheduleId, checked);
        return ResponseEntity.ok(updatedSchedule);
    }

    // 스케줄 알람 켜기, 끄기 반영
    @PatchMapping("/alarm/{scheduleId}")
    public ResponseEntity<ScheduleDTO> toggleScheduleAlarm(@PathVariable("scheduleId") Long scheduleId, @RequestBody Map<String, Boolean> body) {
        Boolean checked = body.get("alarm");
        if (checked == null) {
            return ResponseEntity.badRequest().build();
        }

        ScheduleDTO updatedSchedule = scheduleService.toggleScheduleAlarm(scheduleId, checked);
        return ResponseEntity.ok(updatedSchedule);
    }

    // 스케줄 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("id") Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

}
