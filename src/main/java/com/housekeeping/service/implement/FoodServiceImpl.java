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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<FoodDTO> getAllUserFoods(Long userId) {
        return foodRepository.findAllUserFoods(userId);
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

    @Override
    public boolean deleteUserFood(Long foodId, Long userId) {
        if (foodId == null || userId == null) {
            throw new IllegalArgumentException("식재료 ID와 사용자 ID는 null일 수 없습니다.");
        }

        boolean isDeleted = foodRepository.deleteUserFood(foodId, userId);

        if (!isDeleted) {
            throw new IllegalArgumentException("해당 ID의 식재료를 찾을 수 없거나 사용자에게 권한이 없습니다.");
        }

        return true;
    }

    @Override
    public List<String> getAllIngredientNames() {
        return foodRepository.findAllIngredientNames();
    }

    @Override
    public List<String> getUserIngredients(Long userId) {
        List<FoodDTO> userFoods = foodRepository.findAllUserFoods(userId);
        return userFoods.stream()
                .map(FoodDTO::getFoodName)
                .collect(Collectors.toList());
    }


    @Override
    public List<Food> findFoodsExpiringBetween(LocalDate start, LocalDate end) {
        // 날짜 범위에서 유통기한이 포함된 식재료를 검색하는 쿼리
        return foodRepository.findByFoodExpirationDateBetween(start.atStartOfDay(), end.plusDays(1).atStartOfDay());
    }

}