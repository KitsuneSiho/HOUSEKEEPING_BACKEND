package com.housekeeping.repository;

import com.housekeeping.entity.RoomFurnitureCategory;
import com.housekeeping.entity.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomFurnitureCategoryRepository extends JpaRepository<RoomFurnitureCategory, Long> {

    List<RoomFurnitureCategory> findByRoomType(RoomType roomType);
}
