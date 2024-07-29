package com.housekeeping.service;

import com.housekeeping.DTO.ScheduleDTO;
import com.housekeeping.entity.Schedule;
import com.housekeeping.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 스케줄 리스트를 DTO로 변환하여 반환
    public Map<Long, List<ScheduleDTO>> getSchedulesByRoomIds(List<Long> roomIds) {
        Map<Long, List<ScheduleDTO>> roomSchedules = new HashMap<>();
        for (Long roomId : roomIds) {
            List<Schedule> schedules = scheduleRepository.findByRoom_RoomId(roomId);
            List<ScheduleDTO> scheduleDTOs = schedules.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            roomSchedules.put(roomId, scheduleDTOs);
        }
        return roomSchedules;
    }

    // 스케줄 추가
    public ScheduleDTO addSchedule(ScheduleDTO newScheduleDTO) {
        Schedule newSchedule = convertToEntity(newScheduleDTO);
        Schedule addedSchedule = scheduleRepository.save(newSchedule);
        return convertToDTO(addedSchedule);
    }

    // 스케줄 이름 변경
    public ScheduleDTO updateScheduleName(Long id, String scheduleName) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + id));
        schedule.setScheduleName(scheduleName);
        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return convertToDTO(updatedSchedule);
    }

    // 스케줄 체크 여부 변경
    public ScheduleDTO updateScheduleChecked(Long scheduleId, Boolean checked) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        schedule.setScheduleIsChecked(checked);
        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return convertToDTO(updatedSchedule);
    }

    // 스케줄 삭제
    public void deleteSchedule(Long scheduleId) {
        if (scheduleRepository.existsById(scheduleId)) {
            scheduleRepository.deleteById(scheduleId);
        } else {
            throw new RuntimeException("Schedule not found with id " + scheduleId);
        }
    }

    // 스케줄 알림 켜기, 끄기
    public ScheduleDTO toggleScheduleAlarm(Long scheduleId, boolean isChecked) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id " + scheduleId));
        schedule.setScheduleIsAlarm(isChecked);
        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return convertToDTO(updatedSchedule);
    }

    // DTO를 엔티티로 변환
    private Schedule convertToEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(dto.getScheduleId());
        schedule.setScheduleName(dto.getScheduleName());
        schedule.setScheduleDetail(dto.getScheduleDetail());
        schedule.setScheduleDate(dto.getScheduleDate());
        schedule.setScheduleIsChecked(dto.isScheduleIsChecked());
        schedule.setScheduleIsAlarm(dto.isScheduleIsAlarm());
        // Room 엔티티를 설정하려면 추가 작업이 필요합니다.
        // 예를 들어 Room 엔티티를 조회하여 설정할 수 있습니다.
        return schedule;
    }

    // 엔티티를 DTO로 변환
    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setScheduleId(schedule.getScheduleId());
        dto.setRoomId(schedule.getRoom().getRoomId());
        dto.setScheduleName(schedule.getScheduleName());
        dto.setScheduleDetail(schedule.getScheduleDetail());
        dto.setScheduleDate(schedule.getScheduleDate());
        dto.setScheduleIsChecked(schedule.isScheduleIsChecked());
        dto.setScheduleIsAlarm(schedule.isScheduleIsAlarm());
        return dto;
    }
}
