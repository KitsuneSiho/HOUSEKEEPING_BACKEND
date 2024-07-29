package com.housekeeping.repository.implement;

import com.housekeeping.DTO.FoodDTO;
import com.housekeeping.entity.Food;
import com.housekeeping.entity.QFood;
import com.housekeeping.entity.enums.FoodCategory;
import com.housekeeping.repository.custom.FoodRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class FoodRepositoryImpl implements FoodRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public FoodRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
        //QueryDSL의 핵심 클래스 / JPA 쿼리를 생성하는 데 사용
    }

    @Override
    public List<FoodDTO> findDTOByCategory(FoodCategory category) {
        QFood food = QFood.food;// QueryDSL이 자동 생성한 Food 엔티티의 메타 모델 클래스(Q타입 클래스)
        //Projections.constructor: QueryDSL에서 사용되는 메서드로, 쿼리 결과를 특정 클래스의 생성자를 통해 직접 매핑할 때 사용
        //여기선 DTO 객체로 직접 변환
        return queryFactory
                .select(Projections.constructor(FoodDTO.class,
                        food.foodId,
                        food.foodName,
                        food.foodCategory,
                        food.foodQuantity,
                        food.foodMemo,
                        food.foodExpirationDate))
                .from(food) //쿼리 대상이 되는 앤티티 지정(food)
                .where(food.foodCategory.eq(category)) //주어진 카테고리와 일치하는 Food 엔티티만 필터링
                .fetch(); //쿼리를 실행하고 결과를 List<FoodDTO>형태로 반환
    }
}
