package com.housekeeping.repository;

import com.housekeeping.DTO.RoomDTO;
import com.housekeeping.entity.Room;
import com.housekeeping.repository.custom.RoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryCustom {

    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.roomPollution = :pollution WHERE r.roomId = :roomId")
    void updateRoomPollution(@Param("roomId") Long roomId, @Param("pollution") int pollution);

    List<Room> findByUserUserId(Long userId);
    List<Room> findByRoomIdIn(List<Long> roomIds);
}