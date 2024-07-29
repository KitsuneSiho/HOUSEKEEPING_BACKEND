package com.housekeeping.entity;

import com.housekeeping.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"users", "furniture"})
@EqualsAndHashCode(exclude = {"users", "furniture"})
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
    private List<User> users;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private List<Furniture> furniture;
}