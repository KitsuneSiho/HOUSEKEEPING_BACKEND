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
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routineId;

    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;

    @Column(nullable = false)
    private String routineGroup;

    @Column(nullable = false)
    private String routineName;

    @Column(nullable = false)
    private String routineDetail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoutineFrequency routineFrequency;

    private String routineInterval;

    @Column(nullable = false)
    private boolean routineIsChecked = false;

    // Getters and Setters
}
