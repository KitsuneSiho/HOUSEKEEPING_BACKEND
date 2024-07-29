package com.housekeeping.repository.custom;


import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.Food;
import com.housekeeping.entity.enums.FoodCategory;

import java.util.List;

public interface FoodRepositoryCustom {
    List<FoodDTO> findDTOByCategory(FoodCategory category);
}