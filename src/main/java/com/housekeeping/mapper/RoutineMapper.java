package com.housekeeping.mapper;

import com.housekeeping.DTO.RoutineDTO;
import com.housekeeping.entity.Room;
import com.housekeeping.entity.Routine;
import org.springframework.stereotype.Service;

@Service
public class RoutineMapper {

    // 엔티티를 DTO로 변환
    public RoutineDTO toDTO(Routine routine) {
        return RoutineDTO.builder()
                .routineId(routine.getRoutineId())
                .roomId(routine.getRoom().getRoomId())
                .roomName(routine.getRoom().getRoomName())
                .routineGroupName(routine.getRoutineGroupName())
                .routineName(routine.getRoutineName())
                .routineFrequency(routine.getRoutineFrequency())
                .routineInterval(routine.getRoutineInterval())
                .routineIsChecked(routine.isRoutineIsChecked())
                .routineIsAlarm(routine.isRoutineIsAlarm())
                .build();
    }

    // DTO를 엔티티로 변환
    public Routine toEntity(RoutineDTO routineDTO, Room room) {
        return Routine.builder()
                .routineId(routineDTO.getRoutineId())
                .room(room)
                .routineGroupName(routineDTO.getRoutineGroupName())
                .routineName(routineDTO.getRoutineName())
                .routineFrequency(routineDTO.getRoutineFrequency())
                .routineInterval(routineDTO.getRoutineInterval())
                .routineIsChecked(routineDTO.isRoutineIsChecked())
                .routineIsAlarm(routineDTO.isRoutineIsAlarm())
                .build();
    }
}
