package com.housekeeping.controller;

import com.housekeeping.DTO.TipDTO;
import com.housekeeping.entity.Tip;
import com.housekeeping.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tips")
public class TipController {

    private final TipService tipService;

    @Autowired
    public TipController(TipService tipService) {
        this.tipService = tipService;
    }

    @GetMapping
    public ResponseEntity<List<TipDTO>> getAllTips() {
        List<Tip> tips = tipService.getAllTips();
        List<TipDTO> tipDTOs = tips.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tipDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipDTO> getTipById(@PathVariable Long id) {
        Tip tip = tipService.getTipById(id);
        return ResponseEntity.ok(convertToDTO(tip));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipDTO> updateTip(@PathVariable Long id, @RequestBody TipDTO tipDTO) {
        Tip updatedTip = tipService.updateTip(id, convertToEntity(tipDTO));
        return ResponseEntity.ok(convertToDTO(updatedTip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTip(@PathVariable Long id) {
        tipService.deleteTip(id);
        return ResponseEntity.noContent().build();
    }

    private TipDTO convertToDTO(Tip tip) {
        return TipDTO.builder()
                .tipId(tip.getTipId())
                .tipCategory(tip.getTipCategory())
                .tipTitle(tip.getTipTitle())
                .tipContent(tip.getTipContent())
                .tipViews(tip.getTipViews())
                .tipCreatedDate(tip.getTipCreatedDate())
                .build();
    }

    private Tip convertToEntity(TipDTO tipDTO) {
        return Tip.builder()
                .tipId(tipDTO.getTipId())
                .tipCategory(tipDTO.getTipCategory())
                .tipTitle(tipDTO.getTipTitle())
                .tipContent(tipDTO.getTipContent())
                .tipViews(tipDTO.getTipViews())
                .tipCreatedDate(tipDTO.getTipCreatedDate())
                .build();
    }
}