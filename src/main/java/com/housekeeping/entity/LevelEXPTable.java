package com.housekeeping.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LevelEXPTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long levelId;

    @Column(nullable = false, unique = true)
    private int levelLevel;

    @Column(nullable = false)
    private String levelName;

    @Column(nullable = false)
    private int levelRequireEXP;
}
