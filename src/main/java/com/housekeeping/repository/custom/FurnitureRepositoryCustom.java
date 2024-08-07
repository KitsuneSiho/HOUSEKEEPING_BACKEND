package com.housekeeping.repository.custom;

import com.housekeeping.DTO.FurnitureDTO;
import com.housekeeping.DTO.FurnitureTypeDTO;
import com.housekeeping.entity.enums.FurnitureType;

import java.util.List;

public interface FurnitureRepositoryCustom {

    List<FurnitureDTO> getFurnitureList (int level);
    List<FurnitureTypeDTO> getFurnitureTypeList(int level);
}
