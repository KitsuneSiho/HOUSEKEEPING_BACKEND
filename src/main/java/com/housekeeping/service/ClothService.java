package com.housekeeping.service;

import com.amazonaws.services.s3.AmazonS3;
import com.housekeeping.DTO.ClothDTO;
import com.housekeeping.entity.Cloth;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.ClothSeason;
import com.housekeeping.repository.ClothRepository;
import com.housekeeping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClothService {

    @Autowired
    private ClothRepository clothRepository;

    @Autowired
    private UserRepository userRepository; // UserRepository 주입

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${ncp.bucket.name}")
    private String bucketName;

    @Autowired
    private UserService userService; // UserRepository 주입

    public List<ClothDTO> getClothes(String name, String category, String details, Long userId) {
        List<String> categories = null;

        if (category != null) {
            switch (category.toLowerCase()) {
                case "top":
                    categories = Arrays.asList("반팔", "긴팔", "셔츠", "민소매", "카라티", "니트");
                    break;
                case "outer":
                    categories = Arrays.asList("후드 집업", "가디건", "코트", "패딩", "바람막이");
                    break;
                case "bottom":
                    categories = Arrays.asList("반바지", "긴바지", "스커트", "원피스");
                    break;
                case "bag":
                    categories = Arrays.asList("백팩", "크로스백", "토트백", "숄더백", "웨이스트백");
                    break;
                case "shoes":
                    categories = Arrays.asList("운동화", "스니커즈", "구두", "샌들/슬리퍼");
                    break;
                case "accessory":
                    categories = Arrays.asList("모자", "양말", "선글라스");
                    break;
                default:
                    break;
            }
        }

        return clothRepository.findByUserUserIdAndFilters(userId, name, categories, details)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public ClothDTO saveCloth(ClothDTO clothDTO) {
        try {
            User user = userRepository.findById(clothDTO.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid userId: " + clothDTO.getUserId()));

            Cloth cloth = toEntity(clothDTO, user);
            Cloth savedCloth = clothRepository.save(cloth);
            return toDTO(savedCloth);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to save Cloth. Error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while saving Cloth: " + e.getMessage(), e);
        }
    }

    public ClothDTO updateCloth(Long id, ClothDTO clothDTO) {
        Cloth cloth = clothRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid clothId: " + id));

        cloth.setClothName(clothDTO.getClothName());
        cloth.setClothType(clothDTO.getClothType());
        cloth.setClothColor(clothDTO.getClothColor());
        cloth.setClothMaterial(clothDTO.getClothMaterial());
        cloth.setClothSeason(clothDTO.getClothSeason());
        cloth.setClothCustomTag(clothDTO.getClothCustomTag());
        cloth.setImageUrl(clothDTO.getImageUrl());

        Cloth updatedCloth = clothRepository.save(cloth);
        return toDTO(updatedCloth);
    }

    public void deleteClothAndImage(Long id) {
        try {
            Cloth cloth = clothRepository.findById(id).orElse(null);

            if (cloth != null) {
                String imageUrl = cloth.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                    amazonS3.deleteObject(bucketName, fileName);
                }

                clothRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("옷 삭제 중 오류 발생: " + e.getMessage(), e);
        }
    }

    public List<ClothDTO> getClothesByTemperatureAndUserId(int temperature, Long userId) {
        List<Cloth> allClothes = clothRepository.findByUserUserId(userId);
        return allClothes.stream()
                .filter(cloth -> isSuitableForTemperature(cloth, temperature))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private boolean isSuitableForTemperature(Cloth cloth, int temperature) {
        if (temperature >= 28) {
            return cloth.getClothType().equals("반팔") || cloth.getClothType().equals("셔츠") ||
                    cloth.getClothType().equals("반바지") || cloth.getClothType().equals("스커트") ||
                    cloth.getClothType().equals("샌들/슬리퍼");
        } else if (temperature >= 17 && temperature < 28) {
            return cloth.getClothType().equals("긴팔") || cloth.getClothType().equals("후드티") ||
                    cloth.getClothType().equals("청바지") || cloth.getClothType().equals("운동화");
        } else if (temperature < 17) {
            return cloth.getClothType().equals("니트") || cloth.getClothType().equals("패딩") ||
                    cloth.getClothType().equals("코트") || cloth.getClothType().equals("부츠");
        }
        return false;
    }

    private ClothDTO toDTO(Cloth cloth) {
        ClothDTO dto = new ClothDTO();
        dto.setClothId(cloth.getClothId());
        dto.setUserId(cloth.getUser().getUserId());
        dto.setClothName(cloth.getClothName());
        dto.setClothType(cloth.getClothType());
        dto.setClothColor(cloth.getClothColor());
        dto.setClothMaterial(cloth.getClothMaterial());
        dto.setClothSeason(cloth.getClothSeason());
        dto.setClothCustomTag(cloth.getClothCustomTag());
        dto.setImageUrl(cloth.getImageUrl());
        return dto;
    }

    private Cloth toEntity(ClothDTO clothDTO, User user) {
        Cloth cloth = new Cloth();
        cloth.setUser(user);
        cloth.setClothName(clothDTO.getClothName());
        cloth.setClothType(clothDTO.getClothType());
        cloth.setClothColor(clothDTO.getClothColor());
        cloth.setClothMaterial(clothDTO.getClothMaterial());
        cloth.setClothSeason(clothDTO.getClothSeason());
        cloth.setClothCustomTag(clothDTO.getClothCustomTag());
        cloth.setImageUrl(clothDTO.getImageUrl());
        return cloth;
    }
}