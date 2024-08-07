package com.housekeeping.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Long scheduleId;
    private Long roomId;
    private String scheduleName;
    private String scheduleDetail;
    private LocalDateTime scheduleDate;
    private boolean scheduleIsChecked;
    private boolean scheduleIsAlarm;
}