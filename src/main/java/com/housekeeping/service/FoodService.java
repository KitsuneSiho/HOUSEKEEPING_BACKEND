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
    List<String> getAllIngredientNames(); //레시피 식재료
    List<String> getUserIngredients(Long userId); //레시피 추천용 안쓰이는 것 같은데?
}