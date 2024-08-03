package com.housekeeping.repository.implement;

import com.housekeeping.DTO.RoomDTO;
import com.housekeeping.entity.QRoom;
import com.housekeeping.repository.custom.RoomRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QRoom qRoom = QRoom.room;

    @Override
    public List<RoomDTO> getRoomDTOsByUserId(Long userId) {
        return queryFactory.select(Projections.constructor(RoomDTO.class,
                qRoom.roomId, qRoom.roomName, qRoom.roomType, qRoom.roomPollution, qRoom.schedules, qRoom.roomWallsColor))
                .from(qRoom)
                .where(qRoom.user.userId.eq(userId))
                .fetch();
    }
}
