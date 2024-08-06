package com.housekeeping.service;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.enums.FoodCategory;

import java.util.List;

public interface FoodService {
    List<FoodCategory> getUserCategories(Long userId);
    List<FoodDTO> getAllUserFoods(Long userId);
    List<FoodDTO> getUserFoodsByCategory(Long userId, FoodCategory category);
    FoodDTO addUserFood(FoodDTO foodDTO);
    boolean deleteUserFood(Long foodId, Long userId);
}