package com.housekeeping.repository;

import com.housekeeping.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomRepository extends JpaRepository<Room, Long> {

}