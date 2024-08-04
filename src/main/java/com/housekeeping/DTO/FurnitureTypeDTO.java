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
public class FurnitureTypeDTO {

    private FurnitureType furnitureType;
    private String furnitureTypeName;
}
