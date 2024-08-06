package com.housekeeping.repository;

import com.housekeeping.entity.Room;
import com.housekeeping.repository.custom.RoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByUserUserId(Long userId);
    List<Room> findByRoomIdIn(List<Long> roomIds);
}