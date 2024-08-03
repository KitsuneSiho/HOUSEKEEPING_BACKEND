package com.housekeeping.DTO;

import com.housekeeping.entity.enums.FurnitureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FurnitureDTO {

    private Long furnitureId;
    private int level;
    private String furnitureName;
    private FurnitureType furnitureType;
}
