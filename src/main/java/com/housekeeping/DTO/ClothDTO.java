package com.housekeeping.DTO;

import com.housekeeping.entity.enums.ClothSeason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClothDTO {
    private Long clothId;
    private Long userId; // user 엔티티 대신 userId만 사용
    private String clothName;
    private String clothType;
    private String clothColor;
    private ClothSeason clothSeason;
    private String clothCustomTag;
    private String imageUrl; // 이미지 URL

}
