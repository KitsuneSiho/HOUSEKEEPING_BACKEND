package com.housekeeping.DTO;

import com.housekeeping.entity.Furniture;
import com.housekeeping.entity.Room;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class FurniturePlacementDTO {

    private Long placementId;
    private Long roomId;
    private Long furnitureId;
    private String placementLocation;
}
