package com.housekeeping.service;

import com.housekeeping.entity.Schedule;
import com.housekeeping.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 특정 날짜의 스케줄 수정
    public Schedule updateSchedule(Long scheduleId, Schedule updatedSchedule) {
        if (scheduleRepository.existsById(scheduleId)) {
            updatedSchedule.setScheduleId(scheduleId);
            return scheduleRepository.save(updatedSchedule);
        }
        throw new RuntimeException("Schedule not found with id " + scheduleId);
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
