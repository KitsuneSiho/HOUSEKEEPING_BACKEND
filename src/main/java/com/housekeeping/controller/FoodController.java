package com.housekeeping.controller;
import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.enums.FoodCategory;
import com.housekeeping.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/foods")
public class FoodController {
    private final FoodService foodService;
    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }
    @GetMapping("/categories")
    public List<FoodCategory> getUserFoodCategories(@RequestParam Long userId) {
        return foodService.getUserFoodCategories(userId);
    }
    @GetMapping("/all")
    public List<FoodDTO> getAllUserFoods(@RequestParam Long userId) {
        return foodService.getAllUserFoods(userId);
    }
    @GetMapping("/category")
    public List<FoodDTO> getUserFoodsByCategory(
            @RequestParam Long userId,
            @RequestParam FoodCategory category) {
        return foodService.getUserFoodsByCategory(userId, category);
    }
}