package com.housekeeping.controller;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.enums.FoodCategory;
import com.housekeeping.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/livingroom") //냉장고 초기 페이지
    public List<FoodCategory> getLivingRoom(@RequestParam("userId") Long userId) {
        return foodService.getUserCategories(userId);
    }

    @GetMapping("/foodlist/{category}") //카테고리별 식재료 보기
    public List<FoodDTO> getFoodList(@PathVariable("category") String category, @RequestParam("userId") Long userId) {
        FoodCategory foodCategory = FoodCategory.valueOf(category.toUpperCase());
        return foodService.getUserFoodsByCategory(userId, foodCategory);
    }

    @GetMapping("/foodlist/all") //전체보기
    public List<FoodDTO> getAllFoods(@RequestParam("userId") Long userId, @RequestParam("foodCategory") FoodCategory foodCategory) {
        return foodService.getAllUserFoods(userId, foodCategory);

    }

    @PostMapping("/foodlist/add") //식재료 수동 추가
    public FoodDTO addFood(@RequestBody FoodDTO foodDTO){
        return foodService.addUserFood(foodDTO);
    }

    @PutMapping("/foodlist/update") //식재료 수정
    public FoodDTO updateFood(@RequestBody FoodDTO foodDTO){
        return foodService.addUserFood(foodDTO);
    }


}