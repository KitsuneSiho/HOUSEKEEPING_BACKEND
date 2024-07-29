package com.housekeeping.entity;

import com.housekeeping.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private List<UserEntity> users;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private List<Furniture> furniture;
}
