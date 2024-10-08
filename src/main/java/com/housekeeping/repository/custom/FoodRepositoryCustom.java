package com.housekeeping.repository.custom;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.enums.FoodCategory;

import java.util.List;

public interface FoodRepositoryCustom {
    List<FoodCategory> findUserCategories(Long userId);
    List<FoodDTO> findUserFoods(Long userId, FoodCategory foodCategory);
    List<FoodDTO> findAllUserFoods(Long userId);
    boolean deleteUserFood (Long foodId,Long userId);
    List<String> findAllIngredientNames();
}