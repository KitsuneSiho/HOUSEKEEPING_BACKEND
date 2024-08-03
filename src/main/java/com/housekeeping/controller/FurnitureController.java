package com.housekeeping.controller;

import com.housekeeping.DTO.FurnitureDTO;
import com.housekeeping.entity.Furniture;
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
}
