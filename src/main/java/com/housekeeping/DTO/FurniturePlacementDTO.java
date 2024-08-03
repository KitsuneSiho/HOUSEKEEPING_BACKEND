package com.housekeeping.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class FurniturePlacementDTO {

    private Long placementId;
    private Long roomId;
    private Long furnitureId;
    private String placementLocation;
    private int placementAngle;
    private int placementSize;
}
