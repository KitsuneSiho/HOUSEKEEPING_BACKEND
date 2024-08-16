package com.housekeeping.service;

import com.housekeeping.DTO.RoutineDTO;
import com.housekeeping.DTO.ScheduleDTO;
import com.housekeeping.entity.RecommendRoutine;
import com.housekeeping.entity.Room;
import com.housekeeping.entity.Routine;
import com.housekeeping.entity.Schedule;
import com.housekeeping.mapper.RoutineMapper;
import com.housekeeping.repository.RecommendRoutineRepository;
import com.housekeeping.repository.RoutineRepository;
import com.housekeeping.repository.RoomRepository;
import com.housekeeping.repository.ScheduleRepository;
import com.housekeeping.repository.custom.RoutineRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final ScheduleRepository scheduleRepository;
    private final RoutineMapper routineMapper;
    private final RoomRepository roomRepository;
    private final RecommendRoutineRepository recommendRoutineRepository;

    public List<String> getRoutineGroupNamesByUserId(Long userId) {
        return routineRepository.findDistinctRoutineGroupNamesByUserId(userId);
    }

    public List<RoutineDTO> findByGroupName(String groupName) {
        List<Routine> routines = routineRepository.findByRoutineGroupName(groupName);

        return routines.stream()
                .map(routineMapper::toDTO)  // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }

    // 루틴 추가
    public RoutineDTO addRoutine(RoutineDTO routineDTO) {
        // Room 객체를 RoomRepository에서 가져오기
        Room room = roomRepository.findById(routineDTO.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

        // DTO를 엔티티로 변환
        Routine routine = routineMapper.toEntity(routineDTO, room);
        Routine savedRoutine = routineRepository.save(routine);

        // 저장된 엔티티를 DTO로 변환하여 반환
        return routineMapper.toDTO(savedRoutine);
    }

    // 루틴 그룹 통째로 저장
    public void addRoutineGroup(List<RoutineDTO> routineDTOs) {
        for (RoutineDTO routineDTO : routineDTOs) {
            Room room = roomRepository.findById(routineDTO.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found"));

            Routine routine = new Routine();
            routine.setRoom(room);
            routine.setRoutineGroupName(routineDTO.getRoutineGroupName());
            routine.setRoutineName(routineDTO.getRoutineName());
            routine.setRoutineInterval(routineDTO.getRoutineInterval());
            routine.setRoutineFrequency(routineDTO.getRoutineFrequency());
            routine.setRoutineIsAlarm(routineDTO.isRoutineIsAlarm());
            routine.setRoutineIsChecked(routineDTO.isRoutineIsChecked());
            routineRepository.save(routine);
        }
    }

    // 루틴 수정
    public RoutineDTO updateRoutine(RoutineDTO routineDTO) {

        Routine existingRoutine = routineRepository.findById(routineDTO.getRoutineId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // 엔티티의 필드를 DTO에서 가져온 값으로 업데이트
        existingRoutine.setRoutineName(routineDTO.getRoutineName());
        existingRoutine.setRoutineGroupName(routineDTO.getRoutineGroupName());
        existingRoutine.setRoutineFrequency(routineDTO.getRoutineFrequency());
        existingRoutine.setRoutineInterval(routineDTO.getRoutineInterval());
        existingRoutine.setRoutineIsChecked(routineDTO.isRoutineIsChecked());
        existingRoutine.setRoutineIsAlarm(routineDTO.isRoutineIsAlarm());

        // 업데이트된 엔티티를 데이터베이스에 저장
        Routine updatedRoutine = routineRepository.save(existingRoutine);

        // 업데이트된 엔티티를 DTO로 변환하여 반환합니다.
        return routineMapper.toDTO(updatedRoutine);
    }

    // 루틴 삭제
    public void deleteRoutine(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        routineRepository.delete(routine);
    }

    // 루틴 그룹 통째로 삭제
    public void deleteRoutineGroup(String groupName) {
        // 루틴 그룹 이름으로 모든 루틴 검색 후 삭제
        List<Routine> routines = routineRepository.findByRoutineGroupName(groupName);
        if (routines.isEmpty()) {
            throw new RuntimeException("No routines found for the group: " + groupName);
        }
        routineRepository.deleteAll(routines);
    }

    // 추천 루틴 조회
    public List<RecommendRoutine> getAllRecommendRoutines() {
        return recommendRoutineRepository.findAll();
    }

    // 적용 루틴 변경
    public void updateRoutine(String routineGroupName) {
        // 그룹 명으로 검색해서 해당하는 기존 루틴을 가져오기
        List<Routine> routines = routineRepository.findByRoutineGroupName(routineGroupName);

        // 루틴의 스케줄을 업데이트하거나 추가하는 로직
        List<ScheduleDTO> schedulesToUpdate = new ArrayList<>();

        for (Routine routine : routines) {
            // 루틴의 DTO 변환
            RoutineDTO routineDTO = RoutineDTO.builder()
                    .routineId(routine.getRoutineId())
                    .roomId(routine.getRoom().getRoomId())
                    .routineGroupName(routine.getRoutineGroupName())
                    .routineName(routine.getRoutineName())
                    .routineFrequency(routine.getRoutineFrequency())
                    .routineInterval(routine.getRoutineInterval())
                    .routineIsChecked(true) // 활성화 상태로 설정
                    .routineIsAlarm(routine.isRoutineIsAlarm())
                    .build();

            // 주어진 주기와 주기를 바탕으로 스케줄 생성
            List<ScheduleDTO> scheduleDTOs = createSchedules(routineDTO);
            schedulesToUpdate.addAll(scheduleDTOs);
        }

        // 스케줄 저장
        saveSchedules(schedulesToUpdate);
    }

    private List<ScheduleDTO> createSchedules(RoutineDTO routineDTO) {
        List<ScheduleDTO> schedules = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusYears(1);

        switch (routineDTO.getRoutineFrequency()) {
            case DAILY:
                for (LocalDateTime date = now; date.isBefore(endDate); date = date.plusDays(1)) {
                    schedules.add(createScheduleDTO(routineDTO, date));
                }
                break;
            case WEEKLY:
                String[] intervals = routineDTO.getRoutineInterval().split(",");
                for (LocalDateTime date = now; date.isBefore(endDate); date = date.plusWeeks(1)) {
                    for (String interval : intervals) {
                        // 요일 확인 후 추가
                        if (date.getDayOfWeek().name().equalsIgnoreCase(interval)) {
                            schedules.add(createScheduleDTO(routineDTO, date));
                        }
                    }
                }
                break;
            case MONTHLY:
                String[] dates = routineDTO.getRoutineInterval().split(",");
                for (LocalDateTime date = now; date.isBefore(endDate); date = date.plusMonths(1)) {
                    for (String day : dates) {
                        LocalDateTime scheduleDate = date.withDayOfMonth(Integer.parseInt(day));
                        schedules.add(createScheduleDTO(routineDTO, scheduleDate));
                    }
                }
                break;
        }

        return schedules;
    }

    private ScheduleDTO createScheduleDTO(RoutineDTO routineDTO, LocalDateTime localDateTime) {
        // Convert LocalDateTime to ZonedDateTime with a specific time zone
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Seoul"));

        return ScheduleDTO.builder()
                .scheduleId(null) // 신규 스케줄인 경우 ID는 null
                .roomId(routineDTO.getRoomId())
                .scheduleName(routineDTO.getRoutineName())
                .scheduleDetail("") // 필요한 경우 추가
                .scheduleDate(zonedDateTime) // 저장 시 LocalDateTime으로 변환
                .scheduleIsChecked(false)
                .scheduleIsAlarm(routineDTO.isRoutineIsAlarm())
                .build();
    }

    public void saveSchedules(List<ScheduleDTO> scheduleDTOs) {
        // ScheduleDTO에서 Room 정보를 조회하여 Schedule 엔티티에 설정
        List<Schedule> schedules = scheduleDTOs.stream()
                .map(dto -> {
                    Room room = roomRepository.findById(dto.getRoomId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

                    Routine routine = routineRepository.findById(dto.getRoutineId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));


                    return Schedule.builder()
                            .scheduleId(dto.getScheduleId())
                            .room(room) // Room 객체를 직접 설정
                            .scheduleName(dto.getScheduleName())
                            .scheduleDetail(dto.getScheduleDetail())
                            .scheduleDate(dto.getScheduleDate())
                            .scheduleIsChecked(dto.isScheduleIsChecked())
                            .scheduleIsAlarm(dto.isScheduleIsAlarm())
                            .routine(routine)
                            .build();
                })
                .collect(Collectors.toList());

        scheduleRepository.saveAll(schedules);
    }
    public String getDistinctCheckedRoutineGroupNames(Long userId) {
        return routineRepository.findDistinctCheckedRoutineGroupNamesByUserId(userId);
    }

}
