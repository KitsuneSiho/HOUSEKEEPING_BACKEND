package com.housekeeping.service.implement;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.Food;
import com.housekeeping.entity.enums.FoodCategory;
import com.housekeeping.repository.FoodRepository;
import com.housekeeping.service.FoodService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<FoodDTO> getFoodsByCategory(FoodCategory category) {
        return foodRepository.findDTOByCategory(category);
    }
}