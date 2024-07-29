package com.housekeeping.repository;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.Food;
import com.housekeeping.entity.enums.FoodCategory;
import com.housekeeping.repository.custom.FoodRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryCustom {
    List<FoodDTO> findDTOByFoodCategory(FoodCategory category);
}

