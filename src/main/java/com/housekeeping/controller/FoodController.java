package com.housekeeping.controller;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.enums.FoodCategory;
import com.housekeeping.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
    public List<FoodDTO> getAllFoods(@RequestParam("userId") Long userId) {
        return foodService.getAllUserFoods(userId);

    }

    @PostMapping("/foodlist/add") //식재료 수동 추가
    public FoodDTO addFood(@RequestBody FoodDTO foodDTO){
        return foodService.addUserFood(foodDTO);
    }

    @PutMapping("/foodlist/update") //식재료 수정
    public FoodDTO updateFood(@RequestBody FoodDTO foodDTO){
        return foodService.addUserFood(foodDTO);
    }

    @DeleteMapping("/foodlist/delete/{foodId}") //식재료 삭제
    public ResponseEntity<?> deleteFood(@PathVariable("foodId") Long foodId, @RequestParam("userId") Long userId){
        try {
            boolean isDeleted = foodService.deleteUserFood(foodId, userId);
            if (isDeleted) {
                return ResponseEntity.ok().body("식재료를 삭제했습니다.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("식재료 삭제 중 오류 발생: ", e);
            return ResponseEntity.internalServerError().body("식재료 삭제 중 오류가 발생했습니다.");
        }
    }


}