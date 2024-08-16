package com.housekeeping.controller;

import com.housekeeping.DTO.RoutineDTO;
import com.housekeeping.DTO.ScheduleDTO;
import com.housekeeping.entity.RecommendRoutine;
import com.housekeeping.entity.Routine;
import com.housekeeping.entity.Schedule;
import com.housekeeping.repository.ScheduleRepository;
import com.housekeeping.service.RoutineService;
import com.housekeeping.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/routine")
public class RoutineController {

    @Autowired
    private final RoutineService routineService;

    private final ScheduleService scheduleService;

    private final ScheduleRepository scheduleRepository;

    // 사용자 id에 해당하는 루틴 그룹명 검색
    @GetMapping("/groups")
    public List<String> getRoutineGroupsByUserId(@RequestParam("userId") Long userId) {
        return routineService.getRoutineGroupNamesByUserId(userId);
    }

    // 만들어진 루틴 보기
    @GetMapping("/group/{groupName}")
    public List<RoutineDTO> getRoutinesByGroupName(@PathVariable("groupName") String groupName) {
        return routineService.findByGroupName(groupName);
    }

    // 단일 루틴 추가하기
    @PostMapping("/add")
    public RoutineDTO addRoutine(@RequestBody RoutineDTO routineDTO) {
        // 서비스에서 루틴을 추가하고 DTO로 변환된 결과를 반환
        return routineService.addRoutine(routineDTO);
    }

    // 루틴 그룹 통째로 추가하기
    @PostMapping("/group/add")
    public void addRoutineGroup(@RequestBody List<RoutineDTO> routineDTOs) {
        routineService.addRoutineGroup(routineDTOs);
    }

    // 루틴 수정하기
    @PutMapping("/update")
    public RoutineDTO updateRoutine(@RequestBody RoutineDTO routineDTO) {
        return routineService.updateRoutine(routineDTO);
    }

    // 루틴 삭제하기
    @DeleteMapping("/delete/{routineId}")
    public void deleteRoutine(@PathVariable("routineId") Long routineId) {
        routineService.deleteRoutine(routineId);
    }

    // 루틴 그룹 통째로 삭제하기
    @DeleteMapping("/deleteGroup/{groupName}")
    public ResponseEntity<String> deleteRoutineGroup(@PathVariable("groupName") String groupName) {
        try {
            routineService.deleteRoutineGroup(groupName);
            return ResponseEntity.ok("루틴 그룹이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            // 예외 발생 시 로그 기록 및 에러 메시지 반환
            System.err.println("Error deleting routine group: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("루틴 그룹 삭제 중 오류가 발생했습니다.");
        }
    }

    // 추천 루틴 조회
    @GetMapping("/recommend/view")
    public ResponseEntity<List<RecommendRoutine>> getRecommendRoutines() {
        List<RecommendRoutine> recommendRoutines = routineService.getAllRecommendRoutines();
        return ResponseEntity.ok(recommendRoutines);
    }

    // 루틴 목록 중 적용할 루틴 실제 스케줄에 저장하기
    @PostMapping("/apply")
    public ResponseEntity<String> routineApply(
            @RequestParam("oldRoutineGroupName") String oldRoutineGroupName,
            @RequestParam("newRoutineGroupName") String newRoutineGroupName) {
        try {
            // 현재 날짜부터 1년간의 스케줄 삭제
            LocalDate today = LocalDate.now();
            LocalDate endDate = today.plusYears(1);

            // 1. 기존 루틴 그룹의 스케줄 삭제
            List<RoutineDTO> oldRoutineDTOs = routineService.findByGroupName(oldRoutineGroupName);
            if (oldRoutineDTOs != null) {
                for (RoutineDTO oldRoutineDTO : oldRoutineDTOs) {
                    // 삭제할 스케줄을 조회
                    List<Schedule> oldSchedules = scheduleRepository.findByRoutine_RoutineId(oldRoutineDTO.getRoutineId());
                    if (oldSchedules != null && !oldSchedules.isEmpty()) {
                        scheduleRepository.deleteAll(oldSchedules); // 스케줄 삭제

                        // 이전 루틴들의 적용 상태 다시 false로 변경
                        oldRoutineDTO.setRoutineIsChecked(false);
                        routineService.updateRoutine(oldRoutineDTO);
                    }
                }
            }

            // 2. 새 루틴 그룹에 해당하는 루틴들 조회
            List<RoutineDTO> newRoutineDTOs = routineService.findByGroupName(newRoutineGroupName);
            if (newRoutineDTOs == null || newRoutineDTOs.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("새 루틴 그룹을 찾을 수 없습니다.");
            }

            // 3. 새 루틴들의 routineIsChecked를 true로 업데이트
            newRoutineDTOs.forEach(routineDTO -> {
                routineDTO.setRoutineIsChecked(true);
                routineService.updateRoutine(routineDTO);
            });

            // 4. 현재 날짜부터 1년간의 스케줄 생성
            List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
            for (RoutineDTO routineDTO : newRoutineDTOs) {
                LocalDate startDate = today;
                LocalDate currentDate = startDate;

                switch (routineDTO.getRoutineFrequency()) {
                    case DAILY:
                        while (currentDate.isBefore(endDate)) {
                            scheduleDTOs.add(createScheduleDTO(routineDTO, currentDate));
                            currentDate = currentDate.plusDays(1);
                        }
                        break;

                    case WEEKLY:
                        Set<DayOfWeek> daysOfWeek = parseDaysOfWeek(routineDTO.getRoutineInterval());
                        while (currentDate.isBefore(endDate)) {
                            if (daysOfWeek.contains(currentDate.getDayOfWeek())) {
                                scheduleDTOs.add(createScheduleDTO(routineDTO, currentDate));
                            }
                            currentDate = currentDate.plusDays(1);
                        }
                        break;

                    case MONTHLY:
                        List<Integer> daysOfMonth = parseDaysOfMonth(routineDTO.getRoutineInterval());
                        LocalDate tempDate = today.withDayOfMonth(1);

                        while (tempDate.isBefore(endDate)) {
                            for (Integer day : daysOfMonth) {
                                if (day <= tempDate.lengthOfMonth()) {
                                    LocalDate scheduleDate = tempDate.withDayOfMonth(day);
                                    if (scheduleDate.isBefore(endDate) || scheduleDate.isEqual(endDate)) {
                                        scheduleDTOs.add(createScheduleDTO(routineDTO, scheduleDate));
                                    }
                                }
                            }
                            tempDate = tempDate.plusMonths(1).withDayOfMonth(1);
                        }
                        break;

                    default:
                        throw new IllegalArgumentException("Invalid routine frequency: " + routineDTO.getRoutineFrequency());
                }
            }

            // 5. 생성된 스케줄을 저장
            routineService.saveSchedules(scheduleDTOs);

            return ResponseEntity.ok("루틴이 성공적으로 적용되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("루틴 적용 중 오류가 발생했습니다.");
        }
    }

    // 스케줄 생성용 DTO 생성 메소드
    private ScheduleDTO createScheduleDTO(RoutineDTO routineDTO, LocalDate date) {

        return ScheduleDTO.builder()
                .scheduleId(null) // 신규 스케줄인 경우 ID는 null
                .roomId(routineDTO.getRoomId())
                .scheduleName(routineDTO.getRoutineName())
                .scheduleDetail(routineDTO.getRoutineName())
                .scheduleDate(date.atStartOfDay(ZoneId.of("Asia/Seoul")))
                .scheduleIsChecked(false)
                .scheduleIsAlarm(false)
                .routineId(routineDTO.getRoutineId()) // 루틴 ID 설정
                .build();
    }

    private Set<DayOfWeek> parseDaysOfWeek(String interval) {
        Set<DayOfWeek> daysOfWeek = new HashSet<>();
        for (char day : interval.toCharArray()) {
            switch (day) {
                case '월': daysOfWeek.add(DayOfWeek.MONDAY); break;
                case '화': daysOfWeek.add(DayOfWeek.TUESDAY); break;
                case '수': daysOfWeek.add(DayOfWeek.WEDNESDAY); break;
                case '목': daysOfWeek.add(DayOfWeek.THURSDAY); break;
                case '금': daysOfWeek.add(DayOfWeek.FRIDAY); break;
                case '토': daysOfWeek.add(DayOfWeek.SATURDAY); break;
                case '일': daysOfWeek.add(DayOfWeek.SUNDAY); break;
            }
        }
        return daysOfWeek;
    }

    private List<Integer> parseDaysOfMonth(String interval) {
        return Arrays.stream(interval.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    // 현재 적용되어 있는 루틴 보내기
    @GetMapping("/checked-group-names")
    public ResponseEntity<String> getDistinctCheckedRoutineGroupNames(@RequestParam Long userId) {
        try {
            String groupName = routineService.getDistinctCheckedRoutineGroupNames(userId);
            return ResponseEntity.ok(groupName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
