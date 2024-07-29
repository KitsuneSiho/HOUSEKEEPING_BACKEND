package com.housekeeping.service;

import com.housekeeping.entity.Room;
import com.housekeeping.entity.Schedule;
import com.housekeeping.repository.RoomRepository;
import com.housekeeping.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final RoomRepository roomRepository;

    public Map<Long, List<Schedule>> getSchedulesByRoomIds(List<Long> roomIds) {
        Map<Long, List<Schedule>> roomSchedules = new HashMap<>();
        for (Long roomId : roomIds) {
            List<Schedule> schedules = scheduleRepository.findByRoom_RoomId(roomId);
            roomSchedules.put(roomId, schedules);
        }
        return roomSchedules;
    }

    // 스케줄 추가
    public Schedule addSchedule(Schedule newSchedule) {
        return scheduleRepository.save(newSchedule);
    }

    // 스케줄 이름 변경
    public Schedule updateScheduleName(Long id, String scheduleName) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + id));
        schedule.setScheduleName(scheduleName);
        return scheduleRepository.save(schedule);
    }

    // 스케줄 체크 여부 변경
    public Schedule updateScheduleChecked(Long scheduleId, Boolean checked) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found"));
        schedule.setScheduleIsChecked(checked);
        return scheduleRepository.save(schedule);
    }

    // 특정 날짜의 스케줄 삭제
    public void deleteSchedule(Long scheduleId) {
        if (scheduleRepository.existsById(scheduleId)) {
            scheduleRepository.deleteById(scheduleId);
        } else {
            throw new RuntimeException("Schedule not found with id " + scheduleId);
        }
    }

    // 특정 날짜의 스케줄 알림 켜기, 끄기
    public Schedule toggleScheduleAlarm(Long scheduleId, boolean isChecked) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setScheduleIsChecked(isChecked);
            return scheduleRepository.save(schedule);
        }
        throw new RuntimeException("Schedule not found with id " + scheduleId);
    }
}
