package com.housekeeping.repository.implement;

import com.housekeeping.DTO.RoomColorDTO;
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
    public List<RoomColorDTO> getRoomColorDTOsByUserId(Long userId) {
        return queryFactory.select(Projections.constructor(RoomColorDTO.class,
                qRoom.roomId, qRoom.user.userId, qRoom.roomName, qRoom.roomType, qRoom.roomPollution, qRoom.roomWallsColor))
                .from(qRoom)
                .where(qRoom.user.userId.eq(userId))
                .fetch();
    }
}
