package com.housekeeping.DTO;

import com.housekeeping.entity.enums.FurnitureType;
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
    private String furnitureName;
    private FurnitureType furnitureType;
    private String placementLocation;
    private double placementAngle;
    private double placementSize;
}
