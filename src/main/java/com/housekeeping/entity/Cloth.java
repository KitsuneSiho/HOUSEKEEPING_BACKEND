package com.housekeeping.entity;

import com.housekeeping.entity.enums.ClothSeason;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cloth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clothId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String clothName;

    @Column(nullable = false)
    private String clothType;

    @Column(nullable = false)
    private String clothColor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClothSeason clothSeason;

    private String clothCustomTag;
}
