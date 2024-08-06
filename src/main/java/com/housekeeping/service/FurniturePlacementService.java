package com.housekeeping.service;

import com.housekeeping.DTO.FurniturePlacementDTO;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface FurniturePlacementService {

    // 방에 가구의 위치를 저장
    FurniturePlacementDTO savePlacement(FurniturePlacementDTO furniturePlacementDTO);

    // 방에서 가구를 삭제
    void deletePlacement(Long placementId);

    // 가구의 위치를 반환
    List<FurniturePlacementDTO> getPlacementsByRoomId(Long roomId);
}
