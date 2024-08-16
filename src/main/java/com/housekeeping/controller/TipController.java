package com.housekeeping.controller;

import com.housekeeping.DTO.CommentDTO;
import com.housekeeping.DTO.TipDTO;
import com.housekeeping.entity.Comment;
import com.housekeeping.entity.Tip;
import com.housekeeping.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PostMapping("/save")
    public ResponseEntity<TipDTO> saveTip(@RequestBody TipDTO tipDTO) {
        Tip tip = convertToEntity(tipDTO);
        Tip savedTip = tipService.saveTip(tip);
        return new ResponseEntity<>(convertToDTO(savedTip), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<TipDTO> updateTip(@RequestBody TipDTO tipDTO) {
        Tip updatedTip = tipService.updateTip(tipDTO.getTipId(), convertToEntity(tipDTO));
        return ResponseEntity.ok(convertToDTO(updatedTip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTip(@PathVariable Long id) {
        tipService.deleteTip(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<TipDTO> incrementViewAndGetTip(@PathVariable Long id) {
        Tip updatedTip = tipService.incrementViewAndGetTip(id);
        return ResponseEntity.ok(convertToDTO(updatedTip));
    }

    private TipDTO convertToDTO(Tip tip) {
        TipDTO.TipDTOBuilder builder = TipDTO.builder()
                .tipId(tip.getTipId())
                .tipCategory(tip.getTipCategory())
                .tipTitle(tip.getTipTitle())
                .tipContent(tip.getTipContent())
                .tipViews(tip.getTipViews())
                .tipCreatedDate(tip.getTipCreatedDate());

        if (tip.getComments() != null) {
            builder.comments(tip.getComments().stream()
                    .map(this::convertToCommentDTO)
                    .collect(Collectors.toList()));
        } else {
            builder.comments(new ArrayList<>());
        }

        return builder.build();
    }

    private CommentDTO convertToCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .tipId(comment.getTip().getTipId())
                .userId(comment.getUser().getUserId())
                .commentContent(comment.getCommentContent())
                .commentCreatedDate(comment.getCommentCreatedDate())
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