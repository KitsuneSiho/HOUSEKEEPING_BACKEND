package com.housekeeping.entity;

import com.housekeeping.entity.enums.RoutineFrequency;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routineId;

    // 루틴이 지정될 방
    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;

    // 루틴 그룹명(ex. 내 청소 루틴)
    @Column(nullable = false)
    private String routineGroupName;

    // 루틴 네임(ex. 빨래하기)
    @Column(nullable = false)
    private String routineName;

    // 일간, 주간, 월간
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoutineFrequency routineFrequency;

    // 루틴 주기
    // (주간의 경우 지정할 요일, 월간의 경우 지정할 날짜)
    private String routineInterval;

    // 루틴의 적용 여부
    @Column(nullable = false)
    private boolean routineIsChecked = false;

    // 알람 허용 여부
    @Column(nullable = false)
    private boolean routineIsAlarm = true;

    // Routine이 삭제되면 관련된 Schedule도 삭제되도록 설정
    @OneToMany(mappedBy = "routine", cascade = CascadeType.REMOVE)
    private List<Schedule> schedules;
}
