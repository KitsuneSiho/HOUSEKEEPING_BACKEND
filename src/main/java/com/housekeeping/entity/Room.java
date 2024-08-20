package com.housekeeping.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.housekeeping.entity.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference // 순환 참조 방지
    private User user;

    @Column(nullable = false)
    private String roomName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @Column(nullable = false, columnDefinition = "int default 100") // 기본값을 100으로 설정
    private int roomPollution = 100;

    @Column(columnDefinition = "json", nullable = false)
    private String roomWallsColor;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Routine> routines;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<FurniturePlacement> furniturePlacements;


}
