package com.housekeeping.entity;

import com.housekeeping.entity.enums.RoutineFrequency;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routineId;

    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;

    @Column(nullable = false)
    private String routineGroupName;

    @Column(nullable = false)
    private String routineName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoutineFrequency routineFrequency;

    private String routineInterval;

    @Column(nullable = false)
    private boolean routineIsChecked = false;

    @Column(nullable = false)
    private boolean routineIsAlarm = true;
}
