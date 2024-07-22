package com.housekeeping.entity;

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
public class FurniturePlacement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placementId;

    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "furnitureId", nullable = false)
    private Furniture furniture;

    @Column(nullable = false, columnDefinition = "JSON")
    private String placementLocation;

    @Column(nullable = false)
    private int placementCleaningPeriod;

    // Getters and Setters
}
