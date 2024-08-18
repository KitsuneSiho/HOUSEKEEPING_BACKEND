package com.housekeeping.repository;

import com.housekeeping.DTO.RoomDTO;
import com.housekeeping.entity.Room;
import com.housekeeping.repository.custom.RoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryCustom {

    @Modifying
    @Query("UPDATE Room r SET r.roomPollution = :pollutionValue WHERE r.roomId = :roomId")
    void updateRoomPollution(@Param("roomId") Long roomId, @Param("pollutionValue") int pollutionValue);

    @Query("SELECT new com.housekeeping.DTO.RoomDTO(r.roomId, null, r.roomName, r.roomType, r.roomPollution, null) FROM Room r WHERE r.roomId IN :roomIds")
    List<RoomDTO> findRoomPollutionByRoomIds(@Param("roomIds") List<Long> roomIds);




    List<Room> findByUserUserId(Long userId);
    List<Room> findByRoomIdIn(List<Long> roomIds);
}