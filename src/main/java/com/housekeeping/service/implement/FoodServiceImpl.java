package com.housekeeping.service.implement;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.enums.FoodCategory;
import com.housekeeping.repository.FoodRepository;
import com.housekeeping.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<FoodCategory> getUserFoodCategories(Long userId) {
        return foodRepository.findUserFoodCategories(userId);
    }

    @Override
    public List<FoodDTO> getAllUserFoods(Long userId) {
        return foodRepository.findAllUserFoods(userId);
    }

    @Override
    public List<FoodDTO> getUserFoodsByCategory(Long userId, FoodCategory category) {
        return foodRepository.findUserFoodsByCategory(userId, category);
    }
}