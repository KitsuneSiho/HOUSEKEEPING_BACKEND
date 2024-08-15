package com.housekeeping.service;

import com.housekeeping.entity.RoomFurnitureCategory;

import java.util.List;

public interface RoomFurnitureCategoryService {

    List<List<RoomFurnitureCategory>> getCategoriesByRoomType();
}
