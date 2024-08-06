package com.housekeeping.repository.implement;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.QFood;
import com.housekeeping.entity.enums.FoodCategory;
import com.housekeeping.repository.custom.FoodRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    public List<FoodCategory> findUserCategories(Long userId) {
        QFood food = QFood.food;
        return queryFactory
                .select(food.foodCategory)
                .from(food)
                .where(food.user.userId.eq(userId))
                .distinct()
                .fetch();
    }

    @Override
    public List<FoodDTO> findUserFoods(Long userId, FoodCategory foodCategory) { //전체보기, 카테고리별로 보기
        QFood food = QFood.food;

        BooleanExpression whereCondition = food.user.userId.eq(userId);
        if (foodCategory != null) {
            whereCondition = whereCondition.and(food.foodCategory.eq(foodCategory));
        }

        return queryFactory
                .select(Projections.bean(FoodDTO.class,
                        food.foodId,
                        food.foodName,
                        food.foodCategory,
                        food.foodQuantity,
                        food.foodMemo,
                        food.foodExpirationDate))
                .from(food)
                .where(whereCondition)
                .fetch();
    }

    @Override
    public List<FoodDTO> findAllUserFoods(Long userId) {
        QFood food = QFood.food;

        return queryFactory
                .select(Projections.bean(FoodDTO.class,
                        food.foodId,
                        food.user.userId.as("userId"),
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
    public boolean deleteUserFood(Long foodId, Long userId) {
        QFood food = QFood.food;
        long deletedCount = queryFactory
                .delete(food)
                .where(food.foodId.eq(foodId)
                        .and(food.user.userId.eq(userId)))
                .execute();

        return deletedCount > 0;
    }


}