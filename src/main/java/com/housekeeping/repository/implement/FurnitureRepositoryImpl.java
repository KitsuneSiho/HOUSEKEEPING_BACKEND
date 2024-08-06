package com.housekeeping.repository.implement;

import com.housekeeping.DTO.FurnitureDTO;
import com.housekeeping.DTO.FurnitureTypeDTO;
import com.housekeeping.entity.Furniture;
import com.housekeeping.entity.QFurniture;
import com.housekeeping.entity.enums.FurnitureType;
import com.housekeeping.repository.custom.FurnitureRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FurnitureRepositoryImpl implements FurnitureRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QFurniture qFurniture = QFurniture.furniture;

    @Override
    public List<FurnitureDTO> getFurnitureList(int level) {

        return jpaQueryFactory.select(Projections.constructor(FurnitureDTO.class,
                        qFurniture.furnitureId, qFurniture.level.levelLevel, qFurniture.furnitureName, qFurniture.furnitureType))
                .from(qFurniture)
                .where(qFurniture.level.levelLevel.loe(level))
                .fetch();
    }

    @Override
    public List<FurnitureTypeDTO> getFurnitureTypeList(int level) {

        return jpaQueryFactory.select(Projections.constructor(FurnitureTypeDTO.class,
                        qFurniture.furnitureType, qFurniture.furnitureTypeName))
                .from(qFurniture)
                .where(qFurniture.level.levelLevel.loe(level))
                .groupBy(qFurniture.furnitureType, qFurniture.furnitureTypeName)
            .fetch();
    }
}
