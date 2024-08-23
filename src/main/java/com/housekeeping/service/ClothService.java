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

        List<ClothDTO> suitableClothes = allClothes.stream()
                .filter(cloth -> isSuitableForTemperature(cloth, temperature))
                .map(this::toDTO)
                .collect(Collectors.toList());

        return getRandomRecommendations(suitableClothes);
    }

    private List<ClothDTO> getRandomRecommendations(List<ClothDTO> suitableClothes) {
        List<ClothDTO> selectedClothes = new ArrayList<>();

        List<ClothDTO> tops = filterByCategory(suitableClothes, "top");
        List<ClothDTO> bottoms = filterByCategory(suitableClothes, "bottom");
        List<ClothDTO> outers = filterByCategory(suitableClothes, "outer");

        if (!tops.isEmpty()) {
            ClothDTO selectedTop = getRandomItem(tops);
            selectedClothes.add(selectedTop);

            // 하의와의 색상 호환성 체크
            if (!bottoms.isEmpty()) {
                ClothDTO selectedBottom = getRandomItem(bottoms);
                if (areColorsCompatible(selectedTop.getClothColor(), selectedBottom.getClothColor())) {
                    selectedClothes.add(selectedBottom);
                } else {
                    System.out.println("호환되지 않는 색상 조합 (상의 - 하의): " + selectedTop.getClothColor() + " - " + selectedBottom.getClothColor());
                }
            }

            // 아우터와의 색상 호환성 체크
            if (!outers.isEmpty()) {
                ClothDTO selectedOuter = getRandomItem(outers);
                if (areColorsCompatible(selectedTop.getClothColor(), selectedOuter.getClothColor())) {
                    selectedClothes.add(selectedOuter);
                } else {
                    System.out.println("호환되지 않는 색상 조합 (상의 - 아우터): " + selectedTop.getClothColor() + " - " + selectedOuter.getClothColor());
                }
            }
        }

        List<ClothDTO> shoes = filterByCategory(suitableClothes, "shoes");
        if (!shoes.isEmpty()) selectedClothes.add(getRandomItem(shoes));

        List<ClothDTO> bags = filterByCategory(suitableClothes, "bag");
        if (!bags.isEmpty()) selectedClothes.add(getRandomItem(bags));

        List<ClothDTO> accessories = filterByCategory(suitableClothes, "accessory");
        if (!accessories.isEmpty()) selectedClothes.add(getRandomItem(accessories));

        return selectedClothes;
    }
    public boolean areColorsCompatible(String color1, String color2) {
        // 중립 색상 목록,  조합 색상
        List<String> neutralColors = Arrays.asList("검정", "회색", "흰색", "베이지");
        List<String> compatibleColors = Arrays.asList("초록", "네이비");

        // 호환되지 않는 색상 조합
        Map<String, List<String>> incompatibleColorsMap = new HashMap<>();
        incompatibleColorsMap.put("빨강", Arrays.asList("초록", "네이비"));
        incompatibleColorsMap.put("네이비", Arrays.asList("노랑", "주황"));
        incompatibleColorsMap.put("초록", Arrays.asList("빨강", "핑크","네이비"));
        incompatibleColorsMap.put("주황", Arrays.asList("빨강", "핑크"));

        // 중립 색상은 어떤 색상과도 호환 가능
        if (neutralColors.contains(color1) || neutralColors.contains(color2)) {
            return true;
        }

        // 호환되지 않는 색상 조합이 있는지 확인
        if (incompatibleColorsMap.containsKey(color1) && incompatibleColorsMap.get(color1).contains(color2)) {
            return false;
        }

        if (incompatibleColorsMap.containsKey(color2) && incompatibleColorsMap.get(color2).contains(color1)) {
            return false;
        }

        // 기본 호환 가능 색상
        if (compatibleColors.contains(color1) && compatibleColors.contains(color2)) {
            return true;
        }

        // 호환되지 않는 색상이 아니면서, 호환 가능한 색상도 아닌 경우, 기본적으로 호환
        return true;
    }

    private List<ClothDTO> filterByCategory(List<ClothDTO> clothes, String category) {
        return clothes.stream()
                .filter(cloth -> getCategoriesByType(category).contains(cloth.getClothType()))
                .collect(Collectors.toList());
    }

    private ClothDTO getRandomItem(List<ClothDTO> clothes) {
        Random rand = new Random();
        return clothes.get(rand.nextInt(clothes.size()));
    }

    private boolean isSuitableForTemperature(Cloth cloth, int temperature) {
        if (temperature >= 28) {
            return (isTop(cloth) && (cloth.getClothType().equals("반팔") || cloth.getClothType().equals("민소매"))) ||
                    (isBottom(cloth) && (cloth.getClothType().equals("반바지") || cloth.getClothType().equals("스커트"))) ||
                    (isShoes(cloth) && cloth.getClothType().equals("샌들/슬리퍼")) ||
                    (isAccessory(cloth) && (cloth.getClothType().equals("모자") || cloth.getClothType().equals("선글라스"))) ||
                    (isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백")));
        } else if (temperature >= 23 && temperature < 28) {
            return (isTop(cloth) && (cloth.getClothType().equals("반팔") || cloth.getClothType().equals("긴팔"))) ||
                    (isBottom(cloth) && (cloth.getClothType().equals("긴바지") || cloth.getClothType().equals("반바지"))) ||
                    (isShoes(cloth) && (cloth.getClothType().equals("운동화") || cloth.getClothType().equals("스니커즈"))) ||
                    (isAccessory(cloth) && cloth.getClothType().equals("양말")) ||
                    (isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백")));
        } else if (temperature >= 20 && temperature < 23) {
            return (isTop(cloth) && (cloth.getClothType().equals("긴팔") || cloth.getClothType().equals("셔츠"))) ||
                    (isBottom(cloth) && (cloth.getClothType().equals("반바지") || cloth.getClothType().equals("긴바지"))) ||
                    (isShoes(cloth) && (cloth.getClothType().equals("운동화") || cloth.getClothType().equals("스니커즈"))) ||
                    (isAccessory(cloth) && cloth.getClothType().equals("양말")) ||
                    (isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백")));
        } else if (temperature >= 17 && temperature < 20) {
            return (isTop(cloth) && cloth.getClothType().equals("긴팔")) ||
                    (isBottom(cloth) && cloth.getClothType().equals("긴바지")) ||
                    (isOuter(cloth) && (cloth.getClothType().equals("후드 집업") || cloth.getClothType().equals("바람막이"))) ||
                    (isShoes(cloth) && (cloth.getClothType().equals("운동화") || cloth.getClothType().equals("스니커즈"))) ||
                    (isAccessory(cloth) && cloth.getClothType().equals("양말")) ||
                    (isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백")));
        } else if (temperature >= 12 && temperature < 17) {
            return (isTop(cloth) && (cloth.getClothType().equals("긴팔") || cloth.getClothType().equals("셔츠"))) ||
                    (isBottom(cloth) && cloth.getClothType().equals("긴바지")) ||
                    (isOuter(cloth) && (cloth.getClothType().equals("후드 집업") || cloth.getClothType().equals("바람막이"))) ||
                    (isShoes(cloth) && (cloth.getClothType().equals("운동화") || cloth.getClothType().equals("스니커즈"))) ||
                    (isAccessory(cloth) && cloth.getClothType().equals("양말")) ||
                    (isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백")));
        } else if (temperature >= 5 && temperature < 12) {
            return (isTop(cloth) && cloth.getClothType().equals("니트")) ||
                    (isBottom(cloth) && cloth.getClothType().equals("긴바지")) ||
                    (isShoes(cloth) && (cloth.getClothType().equals("스니커즈") || cloth.getClothType().equals("운동화"))) ||
                    (isOuter(cloth) && (cloth.getClothType().equals("코트") || cloth.getClothType().equals("패딩"))) ||
                    (isAccessory(cloth) && cloth.getClothType().equals("양말")) ||
                    (isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백") || cloth.getClothType().equals("토트백")));
        } else if (temperature < 5) {
            return (isOuter(cloth) && (cloth.getClothType().equals("패딩") || cloth.getClothType().equals("코트"))) ||
                    (isTop(cloth) && (cloth.getClothType().equals("긴팔") || cloth.getClothType().equals("니트"))) ||
                    (isBottom(cloth) && cloth.getClothType().equals("긴바지")) ||
                    (isAccessory(cloth) && cloth.getClothType().equals("양말")) ||
                    (isShoes(cloth) && (cloth.getClothType().equals("스니커즈") || cloth.getClothType().equals("운동화"))) ||
                    (isBag(cloth) && (cloth.getClothType().equals("백팩") || cloth.getClothType().equals("크로스백") || cloth.getClothType().equals("토트백")));
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

