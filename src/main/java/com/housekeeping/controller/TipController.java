package com.housekeeping.controller;

import com.housekeeping.DTO.TipDTO;
import com.housekeeping.entity.Tip;
import com.housekeeping.entity.User;
import com.housekeeping.jwt.JWTUtil;
import com.housekeeping.service.TipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tips")
public class TipController {

    private final TipService tipService;
    private final JWTUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(TipController.class);

    @Autowired
    public TipController(TipService tipService, JWTUtil jwtUtil) {
        this.tipService = tipService;
        this.jwtUtil = jwtUtil;
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
        Long currentUserId = jwtUtil.getCurrentUserId();
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Tip tip = convertToEntity(tipDTO);
        tip.setUser(User.builder().userId(currentUserId).build());
        Tip savedTip = tipService.saveTip(tip);
        return new ResponseEntity<>(convertToDTO(savedTip), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<TipDTO> updateTip(@RequestBody TipDTO tipDTO) {
        log.debug("Received update request for tip: {}", tipDTO);

        if (tipDTO.getTipId() == null) {
            log.error("Received null tipId in update request");
            return ResponseEntity.badRequest().build();
        }

        Long currentUserId = jwtUtil.getCurrentUserId();
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //타유저 수정 방지
        if (!tipService.isAuthorizedToModifyTip(currentUserId, tipDTO.getTipId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Tip tip = convertToEntity(tipDTO);
        tip.setUser(User.builder().userId(currentUserId).build());
        Tip updatedTip = tipService.updateTip(tipDTO.getTipId(), tip);
        return ResponseEntity.ok(convertToDTO(updatedTip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTip(@PathVariable Long id) {
        Long currentUserId = jwtUtil.getCurrentUserId();
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        //타유저 삭제 방지
        if (!tipService.isAuthorizedToModifyTip(currentUserId, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        tipService.deleteTip(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<TipDTO> incrementViewAndGetTip(@PathVariable Long id) {
        Tip updatedTip = tipService.incrementViewAndGetTip(id);
        return ResponseEntity.ok(convertToDTO(updatedTip));
    }

    //UserController로 옮기는게 나은데
    @GetMapping("/current-user")
    public ResponseEntity<Map<String, Long>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            User currentUser = (User) principal;
            Map<String, Long> response = new HashMap<>();
            response.put("userId", currentUser.getUserId());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }



    private TipDTO convertToDTO(Tip tip) {
        return TipDTO.builder()
                .tipId(tip.getTipId())
                .userId(tip.getUser().getUserId())
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