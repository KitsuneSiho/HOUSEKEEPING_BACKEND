package com.housekeeping.service.implement;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.Food;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.FoodCategory;
import com.housekeeping.repository.FoodRepository;
import com.housekeeping.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<FoodCategory> getUserCategories(Long userId) {
        return foodRepository.findUserCategories(userId);
    }

    @Override
    public List<FoodDTO> getAllUserFoods(Long userId, FoodCategory foodCategory) {
        return foodRepository.findUserFoods(userId, foodCategory);
    }

    @Override
    public List<FoodDTO> getUserFoodsByCategory(Long userId, FoodCategory foodCategory) {
        return foodRepository.findUserFoods(userId, foodCategory);
    }

    @Override
    public FoodDTO addUserFood(FoodDTO foodDTO) {
        Food food = foodRepository.save(Food.builder()
                .foodId(foodDTO.getFoodId())
                .user(User.builder().userId(foodDTO.getUserId()).build())
                .foodName(foodDTO.getFoodName())
                .foodQuantity(foodDTO.getFoodQuantity())
                .foodCategory(foodDTO.getFoodCategory())
                .foodMemo(foodDTO.getFoodMemo())
                .foodExpirationDate(foodDTO.getFoodExpirationDate())
                .build());

        return FoodDTO.builder()
                .foodId(food.getFoodId())
                .userId(food.getUser().getUserId())
                .foodName(food.getFoodName())
                .foodQuantity(food.getFoodQuantity())
                .foodCategory(food.getFoodCategory())
                .foodMemo(food.getFoodMemo())
                .foodExpirationDate(food.getFoodExpirationDate())
                .build();
    }
}