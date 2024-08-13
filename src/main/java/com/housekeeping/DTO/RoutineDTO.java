package com.housekeeping.DTO;

import com.housekeeping.entity.enums.RoutineFrequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineDTO {
    private Long routineId;
    private Long roomId;
    private String roomName;
    private String routineGroupName;
    private String routineName;
    private RoutineFrequency routineFrequency;
    private String routineInterval;
    private boolean routineIsChecked;
    private boolean routineIsAlarm;
}