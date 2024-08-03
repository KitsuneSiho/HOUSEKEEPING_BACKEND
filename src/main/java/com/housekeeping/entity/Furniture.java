package com.housekeeping.entity;

import com.housekeeping.entity.enums.FurnitureType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Furniture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long furnitureId;

    @ManyToOne
    @JoinColumn(name = "levelLevel", nullable = false)
    private LevelEXPTable level;

    @Column(nullable = false)
    private String furnitureName;

    @Column(nullable = false)
    private FurnitureType furnitureType;

    @OneToMany(mappedBy = "furniture", cascade = CascadeType.ALL)
    private List<FurniturePlacement> furniturePlacements;
}
