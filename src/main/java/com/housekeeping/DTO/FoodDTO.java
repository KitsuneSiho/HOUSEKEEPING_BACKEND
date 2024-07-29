package com.housekeeping.DTO;

import com.housekeeping.entity.enums.FoodCategory;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodDTO {
    private Long foodId;
    private String foodName;
    private FoodCategory foodCategory;
    private int foodQuantity;
    private String foodMemo;
    private LocalDateTime foodExpirationDate;
}
