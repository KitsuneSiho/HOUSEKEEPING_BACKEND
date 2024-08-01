package com.housekeeping.repository.implement;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.QFood;
import com.housekeeping.entity.enums.FoodCategory;
import com.housekeeping.repository.custom.FoodRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoodRepositoryImpl implements FoodRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public FoodRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<FoodCategory> findUserFoodCategories(Long userId) {
        QFood food = QFood.food;
        return queryFactory
                .select(food.foodCategory)
                .from(food)
                .where(food.user.userId.eq(userId))
                .distinct()
                .fetch();
    }

    @Override
    public List<FoodDTO> findAllUserFoods(Long userId) {
        QFood food = QFood.food;
        return queryFactory
                .select(Projections.constructor(FoodDTO.class,
                        food.foodId,
                        food.foodName,
                        food.foodCategory,
                        food.foodQuantity,
                        food.foodMemo,
                        food.foodExpirationDate))
                .from(food)
                .where(food.user.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<FoodDTO> findUserFoodsByCategory(Long userId, FoodCategory category) {
        QFood food = QFood.food;
        return queryFactory
                .select(Projections.constructor(FoodDTO.class,
                        food.foodId,
                        food.foodName,
                        food.foodCategory,
                        food.foodQuantity,
                        food.foodMemo,
                        food.foodExpirationDate))
                .from(food)
                .where(food.user.userId.eq(userId).and(food.foodCategory.eq(category)))
                .fetch();
    }
}