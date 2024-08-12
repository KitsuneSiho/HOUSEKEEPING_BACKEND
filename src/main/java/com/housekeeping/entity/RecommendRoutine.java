package com.housekeeping.entity;

import com.housekeeping.entity.enums.RoutineFrequency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecommendRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendRoutineId;

    // 루틴이 지정될 방
    private Long roomNo;

    // 루틴 네임(ex. 빨래하기)
    @Column(nullable = false)
    private String routineName;

    // 일간, 주간, 월간
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoutineFrequency routineFrequency;

}
