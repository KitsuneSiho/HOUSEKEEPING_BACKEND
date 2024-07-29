package com.housekeeping.controller;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.enums.FoodCategory;
import com.housekeeping.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<FoodDTO>> getFoodsByCategory(@PathVariable FoodCategory category) {
        List<FoodDTO> foods = foodService.getFoodsByCategory(category);
        return ResponseEntity.ok(foods); // 조회된 음식 목록을 HTTP 200 OK 상태와 함께 반환
    }
}