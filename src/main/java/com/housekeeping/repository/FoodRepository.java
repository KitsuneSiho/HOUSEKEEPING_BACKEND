package com.housekeeping.repository;

import com.housekeeping.entity.Food;
import com.housekeeping.repository.custom.FoodRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryCustom {

}