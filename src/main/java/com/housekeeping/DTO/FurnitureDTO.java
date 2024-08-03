package com.housekeeping.DTO;

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
    private String furnitureType;
}
