package com.housekeeping.service.implement;

import com.housekeeping.DTO.FurniturePlacementDTO;
import com.housekeeping.entity.Furniture;
import com.housekeeping.entity.FurniturePlacement;
import com.housekeeping.entity.Room;
import com.housekeeping.repository.FurniturePlacementRepository;
import com.housekeeping.service.FurniturePlacementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FurniturePlacementServiceImpl implements FurniturePlacementService {

    private final FurniturePlacementRepository furniturePlacementRepository;


    @Override
    public FurniturePlacementDTO savePlacement(FurniturePlacementDTO furniturePlacementDTO) {

        FurniturePlacement furniturePlacement = FurniturePlacement.builder()
                .placementId(furniturePlacementDTO.getPlacementId())
                .room(Room.builder().roomId(furniturePlacementDTO.getRoomId()).build())
                .furniture(Furniture.builder().furnitureId(furniturePlacementDTO.getFurnitureId()).build())
                .placementLocation(furniturePlacementDTO.getPlacementLocation())
                .placementAngle(furniturePlacementDTO.getPlacementAngle())
                .placementSize(furniturePlacementDTO.getPlacementSize())
                .build();

        FurniturePlacement result = furniturePlacementRepository.save(furniturePlacement);

        furniturePlacementDTO.setPlacementId(result.getPlacementId());

        return furniturePlacementDTO;
    }

    @Override
    public void deletePlacement(Long placementId) {

        furniturePlacementRepository.deleteById(placementId);
    }

    @Override
    public List<FurniturePlacementDTO> getPlacementsByRoomId(Long roomId) {

        return furniturePlacementRepository.getFurniturePlacementDTOsByRoomId(roomId);
    }
}
