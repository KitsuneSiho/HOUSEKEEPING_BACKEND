package com.housekeeping.service;

import com.housekeeping.DTO.RoutineDTO;
import com.housekeeping.entity.RecommendRoutine;
import com.housekeeping.entity.Room;
import com.housekeeping.entity.Routine;
import com.housekeeping.mapper.RoutineMapper;
import com.housekeeping.repository.RecommendRoutineRepository;
import com.housekeeping.repository.RoutineRepository;
import com.housekeeping.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final RoutineRepository routineRepository;
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

}
