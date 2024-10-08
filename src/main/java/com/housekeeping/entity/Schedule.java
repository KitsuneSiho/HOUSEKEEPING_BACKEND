package com.housekeeping.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schedule {
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
    private ZonedDateTime scheduleDate;

    @Column(nullable = false)
    private boolean scheduleIsChecked = false;

    @Column(nullable = false)
    private boolean scheduleIsAlarm = true;

    @ManyToOne
    @JoinColumn(name = "routineId", nullable = true)
    private Routine routine;
}
