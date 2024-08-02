package com.housekeeping.DTO;

import com.housekeeping.entity.enums.FoodCategory;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {
    private Long userId;
    private Long foodId;
    private String foodName;
    private FoodCategory foodCategory;
    private int foodQuantity;
    private String foodMemo;
    private LocalDateTime foodExpirationDate;
}
