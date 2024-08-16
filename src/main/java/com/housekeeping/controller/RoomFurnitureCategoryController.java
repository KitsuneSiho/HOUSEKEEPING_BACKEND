package com.housekeeping.controller;

import com.housekeeping.entity.RoomFurnitureCategory;
import com.housekeeping.service.RoomFurnitureCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room-furniture")
public class RoomFurnitureCategoryController {

    private final RoomFurnitureCategoryService roomFurnitureCategoryService;

    @GetMapping("/all")
    public List<List<RoomFurnitureCategory>> getAll() {
        return roomFurnitureCategoryService.getCategoriesByRoomType();
    }
}
