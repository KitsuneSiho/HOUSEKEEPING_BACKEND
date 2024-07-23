package com.housekeeping.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(columnDefinition = "json", nullable = false)
    private String placementLocation;
}
