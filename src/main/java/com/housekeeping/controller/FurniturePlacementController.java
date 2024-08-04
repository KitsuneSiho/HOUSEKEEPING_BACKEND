package com.housekeeping.controller;

import com.housekeeping.DTO.FurniturePlacementDTO;
import com.housekeeping.service.FurniturePlacementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/placement")
@RequiredArgsConstructor
public class FurniturePlacementController {

    private final FurniturePlacementService furniturePlacementService;

    // 방에 배치된 가구 정보를 반환
    @GetMapping("/list")
    public List<FurniturePlacementDTO> placementDTOList(@RequestParam("roomId") Long roomId) {

        return furniturePlacementService.getPlacementsByRoomId(roomId);
    }

    // 방에 배치된 가구 정보를 한꺼번에 반환
    @GetMapping("/list/all")
    public List<List<FurniturePlacementDTO>> placementDTOLists(@RequestParam("roomIds") List<Long> roomIds) {

        List<List<FurniturePlacementDTO>> placementDTOLists = new ArrayList<>();

        for (Long roomId : roomIds) {

            placementDTOLists.add(furniturePlacementService.getPlacementsByRoomId(roomId));
        }

        return placementDTOLists;
    }

    // 방에 가구 배치 정보를 등록
    @PostMapping("/register")
    public FurniturePlacementDTO savePlacement(@RequestBody FurniturePlacementDTO furniturePlacementDTO) {

        return furniturePlacementService.savePlacement(furniturePlacementDTO);
    }

    // 가구 배치 정보를 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlacement(@RequestParam("placementId") Long placementId) {

        furniturePlacementService.deletePlacement(placementId);

        return ResponseEntity.ok().build();
    }
}
