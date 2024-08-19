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
import java.util.*;
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
            categories = getCategoriesByType(category);
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
        Set<String> seenClothTypes = new HashSet<>();

        ClothDTO top = null;
        ClothDTO bottom = null;
        ClothDTO outer = null;
        ClothDTO shoes = null;
        ClothDTO bag = null;
        ClothDTO accessory = null;

        for (Cloth cloth : allClothes) {
            if (isSuitableForTemperature(cloth, temperature)) {
                if (isTop(cloth) && top == null) {
                    top = toDTO(cloth);
                } else if (isBottom(cloth) && bottom == null) {
                    bottom = toDTO(cloth);
                } else if (isOuter(cloth) && outer == null) {
                    outer = toDTO(cloth);
                } else if (isShoes(cloth) && shoes == null) {
                    shoes = toDTO(cloth);
                } else if (isBag(cloth) && bag == null) {
                    bag = toDTO(cloth);
                } else if (isAccessory(cloth) && accessory == null) {
                    accessory = toDTO(cloth);
                }
            }

            // Break out of the loop if we have already selected one item from each category
            if (top != null && bottom != null && outer != null && shoes != null && bag != null && accessory != null) {
                break;
            }
        }

        return Arrays.asList(top, bottom, outer, shoes, bag, accessory)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private boolean isSuitableForTemperature(Cloth cloth, int temperature) {
        // 온도에 따른 의상 추천 로직
        if (temperature >= 28) {
            return isTop(cloth) && (cloth.getClothType().equals("반팔") || cloth.getClothType().equals("셔츠")) ||
                    isBottom(cloth) && (cloth.getClothType().equals("반바지") || cloth.getClothType().equals("스커트")) ||
                    isShoes(cloth) && cloth.getClothType().equals("샌들/슬리퍼") ||
                    isAccessory(cloth) && (cloth.getClothType().equals("모자") || cloth.getClothType().equals("선글라스")) ||
                    isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백") || cloth.getClothType().equals("토트백"));
        } else if (temperature >= 23 && temperature < 28) {
            return isTop(cloth) && (cloth.getClothType().equals("반팔") || cloth.getClothType().equals("셔츠")) ||
                    isBottom(cloth) && cloth.getClothType().equals("긴바지") ||
                    isShoes(cloth) && cloth.getClothType().equals("운동화") ||
                    isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백") || cloth.getClothType().equals("토트백"));
        } else if (temperature >= 17 && temperature < 23) {
            return isTop(cloth) && (cloth.getClothType().equals("긴팔") || cloth.getClothType().equals("카라티")) ||
                    isBottom(cloth) && cloth.getClothType().equals("긴바지") ||
                    isOuter(cloth) && (cloth.getClothType().equals("후드 집업") || cloth.getClothType().equals("바람막이")) ||
                    isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백") || cloth.getClothType().equals("토트백"));
        } else if (temperature >= 12 && temperature < 17) {
            return isTop(cloth) && (cloth.getClothType().equals("긴팔") || cloth.getClothType().equals("니트")) ||
                    isBottom(cloth) && cloth.getClothType().equals("긴바지") ||
                    isOuter(cloth) && (cloth.getClothType().equals("가디건") || cloth.getClothType().equals("코트")) ||
                    isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백") || cloth.getClothType().equals("토트백"));
        } else if (temperature >= 5 && temperature < 12) {
            return isTop(cloth) && cloth.getClothType().equals("니트") ||
                    isBottom(cloth) && cloth.getClothType().equals("긴바지") ||
                    isOuter(cloth) && (cloth.getClothType().equals("코트") || cloth.getClothType().equals("패딩")) ||
                    isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백") || cloth.getClothType().equals("토트백"));
        } else if (temperature < 5) {
            return isOuter(cloth) && (cloth.getClothType().equals("패딩") || cloth.getClothType().equals("코트")) ||
                    isBottom(cloth) && cloth.getClothType().equals("긴바지") ||
                    isAccessory(cloth) && (cloth.getClothType().equals("모자") || cloth.getClothType().equals("양말")) ||
                    isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백") || cloth.getClothType().equals("토트백"));
        }
        return false;
    }

    private boolean isTop(Cloth cloth) {
        return Arrays.asList("반팔", "긴팔", "셔츠", "민소매", "카라티", "니트").contains(cloth.getClothType());
    }

    private boolean isOuter(Cloth cloth) {
        return Arrays.asList("후드 집업", "가디건", "코트", "패딩", "바람막이").contains(cloth.getClothType());
    }

    private boolean isBottom(Cloth cloth) {
        return Arrays.asList("반바지", "긴바지", "스커트", "원피스").contains(cloth.getClothType());
    }

    private boolean isShoes(Cloth cloth) {
        return Arrays.asList("운동화", "스니커즈", "구두", "샌들/슬리퍼").contains(cloth.getClothType());
    }

    private boolean isBag(Cloth cloth) {
        return Arrays.asList("백팩", "크로스백", "토트백", "숄더백", "웨이스트백").contains(cloth.getClothType());
    }

    private boolean isAccessory(Cloth cloth) {
        return Arrays.asList("모자", "양말", "선글라스").contains(cloth.getClothType());
    }

    private List<String> getCategoriesByType(String category) {
        switch (category.toLowerCase()) {
            case "top":
                return Arrays.asList("반팔", "긴팔", "셔츠", "민소매", "카라티", "니트");
            case "outer":
                return Arrays.asList("후드 집업", "가디건", "코트", "패딩", "바람막이");
            case "bottom":
                return Arrays.asList("반바지", "긴바지", "스커트", "원피스");
            case "bag":
                return Arrays.asList("백팩", "크로스백", "토트백", "숄더백", "웨이스트백");
            case "shoes":
                return Arrays.asList("운동화", "스니커즈", "구두", "샌들/슬리퍼");
            case "accessory":
                return Arrays.asList("모자", "양말", "선글라스");
            default:
                return null;
        }
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