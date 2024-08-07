package com.housekeeping.controller;

import com.housekeeping.DTO.FurnitureDTO;
import com.housekeeping.DTO.FurnitureTypeDTO;
import com.housekeeping.entity.enums.FurnitureType;
import com.housekeeping.service.FurnitureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/furniture")
public class FurnitureController {

    private final FurnitureService furnitureService;

    @GetMapping("/list/{level}")
    public List<FurnitureDTO> getFurnitureList(@PathVariable("level") int level) {
        return furnitureService.getFurnitureList(level);
    }

    @GetMapping("/type/list/{level}")
    public List<FurnitureTypeDTO> getFurnitureTypeList(@PathVariable("level") int level) {
        return furnitureService.getFurnitureTypeList(level);
    }
}
