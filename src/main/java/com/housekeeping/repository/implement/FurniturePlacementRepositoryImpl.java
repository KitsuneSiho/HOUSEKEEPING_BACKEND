package com.housekeeping.repository.implement;

import com.housekeeping.DTO.FurnitureDTO;
import com.housekeeping.DTO.FurniturePlacementDTO;
import com.housekeeping.entity.QFurniturePlacement;
import com.housekeeping.repository.custom.FurniturePlacementRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FurniturePlacementRepositoryImpl implements FurniturePlacementRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    QFurniturePlacement qFurniturePlacement = QFurniturePlacement.furniturePlacement;

    @Override
    public List<FurniturePlacementDTO> getFurniturePlacementDTOsByRoomId(Long roomId) {
        return jpaQueryFactory.select(Projections.constructor(FurniturePlacementDTO.class,
                        qFurniturePlacement.placementId, qFurniturePlacement.room.roomId, qFurniturePlacement.furniture.furnitureId,
                        qFurniturePlacement.furniture.furnitureName, qFurniturePlacement.furniture.furnitureType, qFurniturePlacement.placementLocation,
                        qFurniturePlacement.placementAngle, qFurniturePlacement.placementSize))
                .from(qFurniturePlacement)
                .where(qFurniturePlacement.room.roomId.eq(roomId))
                .fetch();
    }
}
