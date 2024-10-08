package com.housekeeping.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoutineSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;

    @Column(nullable = false)
    private String scheduleName;

    @Column(nullable = false)
    private String scheduleDetail;

    @Column(nullable = false)
    private LocalDateTime scheduleDate;

    @Column(nullable = false)
    private boolean scheduleIsChecked = false;

    @Column(nullable = false)
    private boolean scheduleIsAlarm = true;

    @ManyToOne
    @JoinColumn(name = "routineId", nullable = false)
    private Routine routine;
}
