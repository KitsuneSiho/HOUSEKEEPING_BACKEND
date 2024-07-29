package com.housekeeping.service;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.enums.FoodCategory;

import java.util.List;

public interface FoodService {
    List<FoodDTO> getFoodsByCategory(FoodCategory category);
}