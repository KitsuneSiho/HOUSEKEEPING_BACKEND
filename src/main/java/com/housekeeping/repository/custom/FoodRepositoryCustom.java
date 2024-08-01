package com.housekeeping.repository.custom;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.enums.FoodCategory;

import java.util.List;

public interface FoodRepositoryCustom {
    List<FoodCategory> findUserFoodCategories(Long userId);
    List<FoodDTO> findAllUserFoods(Long userId);
    List<FoodDTO> findUserFoodsByCategory(Long userId, FoodCategory category);
}