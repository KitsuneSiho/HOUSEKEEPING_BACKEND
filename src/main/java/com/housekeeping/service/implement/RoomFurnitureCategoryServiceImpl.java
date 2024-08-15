package com.housekeeping.service.implement;

import com.housekeeping.entity.RoomFurnitureCategory;
import com.housekeeping.entity.enums.RoomType;
import com.housekeeping.repository.RoomFurnitureCategoryRepository;
import com.housekeeping.service.RoomFurnitureCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomFurnitureCategoryServiceImpl implements RoomFurnitureCategoryService {

    private final RoomFurnitureCategoryRepository roomFurnitureCategoryRepository;

    @Override
    public List<List<RoomFurnitureCategory>> getCategoriesByRoomType() {
        return List.of(roomFurnitureCategoryRepository.findByRoomType(RoomType.PRIVATE),
                roomFurnitureCategoryRepository.findByRoomType(RoomType.KITCHEN),
                roomFurnitureCategoryRepository.findByRoomType(RoomType.TOILET));
    }
}
