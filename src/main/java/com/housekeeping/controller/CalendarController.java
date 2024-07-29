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

    @PostMapping("/view")
    public ResponseEntity<?> getUserSchedules(@RequestBody List<Long> roomIds) {
        if (roomIds == null || roomIds.isEmpty()) {
            return ResponseEntity.badRequest().body("방이없다");
        }

        Map<Long, List<ScheduleDTO>> schedules = scheduleService.getSchedulesByRoomIds(roomIds);
        return ResponseEntity.ok(schedules);
    }

    @PostMapping("/add")
    public ResponseEntity<ScheduleDTO> addSchedule(@RequestBody ScheduleDTO newScheduleDTO) {
        ScheduleDTO addedSchedule = scheduleService.addSchedule(newScheduleDTO);
        return ResponseEntity.ok(addedSchedule);
    }

    @PatchMapping("/updateName/{id}")
    public ResponseEntity<ScheduleDTO> updateScheduleName(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String scheduleName = payload.get("scheduleName");
        ScheduleDTO updatedSchedule = scheduleService.updateScheduleName(id, scheduleName);
        return ResponseEntity.ok(updatedSchedule);
    }

    @PatchMapping("/updateChecked/{scheduleId}")
    public ResponseEntity<ScheduleDTO> updateChecked(@PathVariable Long scheduleId, @RequestBody Map<String, Boolean> body) {
        Boolean checked = body.get("checked");
        if (checked == null) {
            return ResponseEntity.badRequest().build();
        }
        ScheduleDTO updatedSchedule = scheduleService.updateScheduleChecked(scheduleId, checked);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/alarm/{id}")
    public ResponseEntity<ScheduleDTO> toggleScheduleAlarm(@PathVariable Long id, @RequestParam boolean isChecked) {
        ScheduleDTO updatedSchedule = scheduleService.toggleScheduleAlarm(id, isChecked);
        return ResponseEntity.ok(updatedSchedule);
    }
}
