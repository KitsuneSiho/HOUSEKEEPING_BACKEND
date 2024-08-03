package com.housekeeping.repository.custom;

import com.housekeeping.DTO.FurniturePlacementDTO;

import java.util.List;

public interface FurniturePlacementRepositoryCustom {

    List<FurniturePlacementDTO> getFurniturePlacementDTOsByRoomId(Long roomId);
}
