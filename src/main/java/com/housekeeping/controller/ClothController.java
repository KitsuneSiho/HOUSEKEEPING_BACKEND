package com.housekeeping.controller;

import com.housekeeping.DTO.ClothDTO;
import com.housekeeping.entity.Cloth;
import com.housekeeping.service.ClothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ware")
public class ClothController {

    @Autowired
    private ClothService clothService;

    //옷장 아이템 조회
    @GetMapping("/items")
    public List<ClothDTO> getClothes(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "category", required = false) String category,
                                     @RequestParam(value = "details", required = false) String details) {
        return clothService.getClothes(name, category, details);
    }

    // 옷 아이템 저장
    @PostMapping("/items")
    public ResponseEntity<ClothDTO> saveCloth(@RequestBody ClothDTO clothDTO) {
        try {
            System.out.println("옷 저장에 대한 요청을 받았다. " + clothDTO);
            ClothDTO savedCloth = clothService.saveCloth(clothDTO);
            return ResponseEntity.ok(savedCloth);
        } catch (Exception e) {
            System.err.println("옷 저장 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 옷 아이템 수정
    @PutMapping("/items/{id}")
    public ResponseEntity<ClothDTO> updateCloth(@PathVariable("id") Long id, @RequestBody ClothDTO clothDTO) {
        try {
            ClothDTO updatedCloth = clothService.updateCloth(id, clothDTO);
            return ResponseEntity.ok(updatedCloth);
        } catch (Exception e) {
            System.err.println("옷 수정 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 옷 아이템 삭제
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteCloth(@PathVariable("id") Long id) {
        try {
            clothService.deleteClothAndImage(id); // 변경된 부분
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println("옷 삭제 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<ClothDTO>> getRecommendedClothes(
            @RequestParam int temperature,
            @RequestParam(name = "user_id") Long userId) {

        List<ClothDTO> recommendedClothes = clothService.getClothesByTemperatureAndUserId(temperature, userId);
        return ResponseEntity.ok(recommendedClothes);
    }
}