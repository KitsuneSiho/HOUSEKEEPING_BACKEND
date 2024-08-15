package com.housekeeping.config;

import com.housekeeping.entity.Furniture;
import com.housekeeping.entity.LevelEXPTable;
import com.housekeeping.entity.RoomFurnitureCategory;
import com.housekeeping.entity.enums.FurnitureType;
import com.housekeeping.entity.enums.RoomType;
import com.housekeeping.repository.FurnitureRepository;
import com.housekeeping.repository.LevelEXPTableRepository;
import com.housekeeping.repository.RoomFurnitureCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class FurnitureInitializer {

    @Bean
    @Order(2)
    public CommandLineRunner initializeFurniture(FurnitureRepository furnitureRepository, RoomFurnitureCategoryRepository roomFurnitureCategoryRepository, LevelEXPTableRepository levelRepository) {
        return (args) -> {
            if (furnitureRepository.count() == 0) {
                // 레벨 정보 가져오기
                LevelEXPTable level1 = levelRepository.findByLevelLevel(1)
                        .orElseGet(() -> levelRepository.save(LevelEXPTable.builder().levelLevel(1).levelName("초보자").levelRequireEXP(0).build()));

                // 가구 데이터 초기화
                furnitureRepository.save(Furniture.builder().furnitureName("게시판").furnitureType(FurnitureType.WALL).level(level1).furnitureTypeName("벽").build());
                furnitureRepository.save(Furniture.builder().furnitureName("문1").furnitureType(FurnitureType.WALL).level(level1).furnitureTypeName("벽").build());
                furnitureRepository.save(Furniture.builder().furnitureName("문2").furnitureType(FurnitureType.WALL).level(level1).furnitureTypeName("벽").build());
                furnitureRepository.save(Furniture.builder().furnitureName("문3").furnitureType(FurnitureType.WALL).level(level1).furnitureTypeName("벽").build());
                furnitureRepository.save(Furniture.builder().furnitureName("문4").furnitureType(FurnitureType.WALL).level(level1).furnitureTypeName("벽").build());
                furnitureRepository.save(Furniture.builder().furnitureName("문5").furnitureType(FurnitureType.WALL).level(level1).furnitureTypeName("벽").build());
                furnitureRepository.save(Furniture.builder().furnitureName("창문1").furnitureType(FurnitureType.WALL).level(level1).furnitureTypeName("벽").build());
                furnitureRepository.save(Furniture.builder().furnitureName("창문2").furnitureType(FurnitureType.WALL).level(level1).furnitureTypeName("벽").build());
                furnitureRepository.save(Furniture.builder().furnitureName("창문3").furnitureType(FurnitureType.WALL).level(level1).furnitureTypeName("벽").build());
                furnitureRepository.save(Furniture.builder().furnitureName("커튼1").furnitureType(FurnitureType.WALL).level(level1).furnitureTypeName("벽").build());

                furnitureRepository.save(Furniture.builder().furnitureName("러그1").furnitureType(FurnitureType.FLOOR).level(level1).furnitureTypeName("바닥").build());
                furnitureRepository.save(Furniture.builder().furnitureName("러그2").furnitureType(FurnitureType.FLOOR).level(level1).furnitureTypeName("바닥").build());

                furnitureRepository.save(Furniture.builder().furnitureName("책상1").furnitureType(FurnitureType.DESK).level(level1).furnitureTypeName("책상").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책상2").furnitureType(FurnitureType.DESK).level(level1).furnitureTypeName("책상").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책상3").furnitureType(FurnitureType.DESK).level(level1).furnitureTypeName("책상").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책상4").furnitureType(FurnitureType.DESK).level(level1).furnitureTypeName("책상").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책상5").furnitureType(FurnitureType.DESK).level(level1).furnitureTypeName("책상").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책상6").furnitureType(FurnitureType.DESK).level(level1).furnitureTypeName("책상").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책상7").furnitureType(FurnitureType.DESK).level(level1).furnitureTypeName("책상").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책상8").furnitureType(FurnitureType.DESK).level(level1).furnitureTypeName("책상").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책상9").furnitureType(FurnitureType.DESK).level(level1).furnitureTypeName("책상").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책상10").furnitureType(FurnitureType.DESK).level(level1).furnitureTypeName("책상").build());

                furnitureRepository.save(Furniture.builder().furnitureName("침대1").furnitureType(FurnitureType.BED).level(level1).furnitureTypeName("침대").build());
                furnitureRepository.save(Furniture.builder().furnitureName("침대2").furnitureType(FurnitureType.BED).level(level1).furnitureTypeName("침대").build());
                furnitureRepository.save(Furniture.builder().furnitureName("침대3").furnitureType(FurnitureType.BED).level(level1).furnitureTypeName("침대").build());
                furnitureRepository.save(Furniture.builder().furnitureName("침대4").furnitureType(FurnitureType.BED).level(level1).furnitureTypeName("침대").build());
                furnitureRepository.save(Furniture.builder().furnitureName("침대5").furnitureType(FurnitureType.BED).level(level1).furnitureTypeName("침대").build());
                furnitureRepository.save(Furniture.builder().furnitureName("침대6").furnitureType(FurnitureType.BED).level(level1).furnitureTypeName("침대").build());
                furnitureRepository.save(Furniture.builder().furnitureName("침대7").furnitureType(FurnitureType.BED).level(level1).furnitureTypeName("침대").build());
                furnitureRepository.save(Furniture.builder().furnitureName("침대8").furnitureType(FurnitureType.BED).level(level1).furnitureTypeName("침대").build());

                furnitureRepository.save(Furniture.builder().furnitureName("소파1").furnitureType(FurnitureType.SOFA).level(level1).furnitureTypeName("소파").build());
                furnitureRepository.save(Furniture.builder().furnitureName("소파2").furnitureType(FurnitureType.SOFA).level(level1).furnitureTypeName("소파").build());
                furnitureRepository.save(Furniture.builder().furnitureName("소파3").furnitureType(FurnitureType.SOFA).level(level1).furnitureTypeName("소파").build());
                furnitureRepository.save(Furniture.builder().furnitureName("소파4").furnitureType(FurnitureType.SOFA).level(level1).furnitureTypeName("소파").build());
                furnitureRepository.save(Furniture.builder().furnitureName("소파5").furnitureType(FurnitureType.SOFA).level(level1).furnitureTypeName("소파").build());
                furnitureRepository.save(Furniture.builder().furnitureName("소파6").furnitureType(FurnitureType.SOFA).level(level1).furnitureTypeName("소파").build());
                furnitureRepository.save(Furniture.builder().furnitureName("소파7").furnitureType(FurnitureType.SOFA).level(level1).furnitureTypeName("소파").build());
                furnitureRepository.save(Furniture.builder().furnitureName("소파8").furnitureType(FurnitureType.SOFA).level(level1).furnitureTypeName("소파").build());
                furnitureRepository.save(Furniture.builder().furnitureName("소파9").furnitureType(FurnitureType.SOFA).level(level1).furnitureTypeName("소파").build());
                furnitureRepository.save(Furniture.builder().furnitureName("소파10").furnitureType(FurnitureType.SOFA).level(level1).furnitureTypeName("소파").build());
                furnitureRepository.save(Furniture.builder().furnitureName("소파11").furnitureType(FurnitureType.SOFA).level(level1).furnitureTypeName("소파").build());

                furnitureRepository.save(Furniture.builder().furnitureName("옷장1").furnitureType(FurnitureType.CLOSET).level(level1).furnitureTypeName("옷장").build());
                furnitureRepository.save(Furniture.builder().furnitureName("옷장2").furnitureType(FurnitureType.CLOSET).level(level1).furnitureTypeName("옷장").build());

                furnitureRepository.save(Furniture.builder().furnitureName("서랍장1").furnitureType(FurnitureType.DRAWER).level(level1).furnitureTypeName("서랍장").build());
                furnitureRepository.save(Furniture.builder().furnitureName("서랍장2").furnitureType(FurnitureType.DRAWER).level(level1).furnitureTypeName("서랍장").build());
                furnitureRepository.save(Furniture.builder().furnitureName("서랍장3").furnitureType(FurnitureType.DRAWER).level(level1).furnitureTypeName("서랍장").build());
                furnitureRepository.save(Furniture.builder().furnitureName("서랍장4").furnitureType(FurnitureType.DRAWER).level(level1).furnitureTypeName("서랍장").build());
                furnitureRepository.save(Furniture.builder().furnitureName("서랍장5").furnitureType(FurnitureType.DRAWER).level(level1).furnitureTypeName("서랍장").build());
                furnitureRepository.save(Furniture.builder().furnitureName("서랍장6").furnitureType(FurnitureType.DRAWER).level(level1).furnitureTypeName("서랍장").build());
                furnitureRepository.save(Furniture.builder().furnitureName("서랍장7").furnitureType(FurnitureType.DRAWER).level(level1).furnitureTypeName("서랍장").build());

                furnitureRepository.save(Furniture.builder().furnitureName("책장1").furnitureType(FurnitureType.BOOKSHELF).level(level1).furnitureTypeName("책장").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책장2").furnitureType(FurnitureType.BOOKSHELF).level(level1).furnitureTypeName("책장").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책장3").furnitureType(FurnitureType.BOOKSHELF).level(level1).furnitureTypeName("책장").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책장4").furnitureType(FurnitureType.BOOKSHELF).level(level1).furnitureTypeName("책장").build());
                furnitureRepository.save(Furniture.builder().furnitureName("책장5").furnitureType(FurnitureType.BOOKSHELF).level(level1).furnitureTypeName("책장").build());

                furnitureRepository.save(Furniture.builder().furnitureName("의자1").furnitureType(FurnitureType.CHAIR).level(level1).furnitureTypeName("의자").build());
                furnitureRepository.save(Furniture.builder().furnitureName("의자3").furnitureType(FurnitureType.CHAIR).level(level1).furnitureTypeName("의자").build());
                furnitureRepository.save(Furniture.builder().furnitureName("의자4").furnitureType(FurnitureType.CHAIR).level(level1).furnitureTypeName("의자").build());
                furnitureRepository.save(Furniture.builder().furnitureName("의자5").furnitureType(FurnitureType.CHAIR).level(level1).furnitureTypeName("의자").build());
                furnitureRepository.save(Furniture.builder().furnitureName("의자6").furnitureType(FurnitureType.CHAIR).level(level1).furnitureTypeName("의자").build());

                furnitureRepository.save(Furniture.builder().furnitureName("쓰레기통1").furnitureType(FurnitureType.TRASHCAN).level(level1).furnitureTypeName("쓰레기통").build());
                furnitureRepository.save(Furniture.builder().furnitureName("쓰레기통2").furnitureType(FurnitureType.TRASHCAN).level(level1).furnitureTypeName("쓰레기통").build());
                furnitureRepository.save(Furniture.builder().furnitureName("쓰레기통3").furnitureType(FurnitureType.TRASHCAN).level(level1).furnitureTypeName("쓰레기통").build());
                furnitureRepository.save(Furniture.builder().furnitureName("쓰레기통4").furnitureType(FurnitureType.TRASHCAN).level(level1).furnitureTypeName("쓰레기통").build());
                furnitureRepository.save(Furniture.builder().furnitureName("쓰레기통5").furnitureType(FurnitureType.TRASHCAN).level(level1).furnitureTypeName("쓰레기통").build());

                furnitureRepository.save(Furniture.builder().furnitureName("식물1").furnitureType(FurnitureType.DECORATION).level(level1).furnitureTypeName("장식품").build());
                furnitureRepository.save(Furniture.builder().furnitureName("식물2").furnitureType(FurnitureType.DECORATION).level(level1).furnitureTypeName("장식품").build());
                furnitureRepository.save(Furniture.builder().furnitureName("식물3").furnitureType(FurnitureType.DECORATION).level(level1).furnitureTypeName("장식품").build());
                furnitureRepository.save(Furniture.builder().furnitureName("식물4").furnitureType(FurnitureType.DECORATION).level(level1).furnitureTypeName("장식품").build());
                furnitureRepository.save(Furniture.builder().furnitureName("식물5").furnitureType(FurnitureType.DECORATION).level(level1).furnitureTypeName("장식품").build());
                furnitureRepository.save(Furniture.builder().furnitureName("식물6").furnitureType(FurnitureType.DECORATION).level(level1).furnitureTypeName("장식품").build());
                furnitureRepository.save(Furniture.builder().furnitureName("식물7").furnitureType(FurnitureType.DECORATION).level(level1).furnitureTypeName("장식품").build());
                furnitureRepository.save(Furniture.builder().furnitureName("식물8").furnitureType(FurnitureType.DECORATION).level(level1).furnitureTypeName("장식품").build());
                furnitureRepository.save(Furniture.builder().furnitureName("장식품1").furnitureType(FurnitureType.DECORATION).level(level1).furnitureTypeName("장식품").build());
                furnitureRepository.save(Furniture.builder().furnitureName("장식품2").furnitureType(FurnitureType.DECORATION).level(level1).furnitureTypeName("장식품").build());

                furnitureRepository.save(Furniture.builder().furnitureName("조명1").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명2").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명3").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명4").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명5").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명6").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명7").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명8").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명9").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명10").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명11").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명12").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명13").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명14").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());
                furnitureRepository.save(Furniture.builder().furnitureName("조명15").furnitureType(FurnitureType.LIGHT).level(level1).furnitureTypeName("조명").build());

                furnitureRepository.save(Furniture.builder().furnitureName("피카츄").furnitureType(FurnitureType.POKEMON).level(level1).furnitureTypeName("포켓몬").build());
                furnitureRepository.save(Furniture.builder().furnitureName("폴리곤").furnitureType(FurnitureType.POKEMON).level(level1).furnitureTypeName("포켓몬").build());
                furnitureRepository.save(Furniture.builder().furnitureName("파이리").furnitureType(FurnitureType.POKEMON).level(level1).furnitureTypeName("포켓몬").build());
                furnitureRepository.save(Furniture.builder().furnitureName("탕구리").furnitureType(FurnitureType.POKEMON).level(level1).furnitureTypeName("포켓몬").build());
                furnitureRepository.save(Furniture.builder().furnitureName("코일").furnitureType(FurnitureType.POKEMON).level(level1).furnitureTypeName("포켓몬").build());
                furnitureRepository.save(Furniture.builder().furnitureName("잠만보").furnitureType(FurnitureType.POKEMON).level(level1).furnitureTypeName("포켓몬").build());
                furnitureRepository.save(Furniture.builder().furnitureName("이브이").furnitureType(FurnitureType.POKEMON).level(level1).furnitureTypeName("포켓몬").build());
                furnitureRepository.save(Furniture.builder().furnitureName("잉어킹").furnitureType(FurnitureType.POKEMON).level(level1).furnitureTypeName("포켓몬").build());
                furnitureRepository.save(Furniture.builder().furnitureName("꼬지모").furnitureType(FurnitureType.POKEMON).level(level1).furnitureTypeName("포켓몬").build());
                furnitureRepository.save(Furniture.builder().furnitureName("뮤").furnitureType(FurnitureType.POKEMON).level(level1).furnitureTypeName("포켓몬").build());
                furnitureRepository.save(Furniture.builder().furnitureName("몬스터볼").furnitureType(FurnitureType.POKEMON).level(level1).furnitureTypeName("포켓몬").build());

                furnitureRepository.save(Furniture.builder().furnitureName("냉장고1").furnitureType(FurnitureType.REFRIGERATOR).level(level1).furnitureTypeName("냉장고").build());

                furnitureRepository.save(Furniture.builder().furnitureName("세탁기1").furnitureType(FurnitureType.WASHINGMACHINE).level(level1).furnitureTypeName("세탁기").build());

                furnitureRepository.save(Furniture.builder().furnitureName("싱크대1").furnitureType(FurnitureType.SINK).level(level1).furnitureTypeName("싱크대").build());
                furnitureRepository.save(Furniture.builder().furnitureName("세면대1").furnitureType(FurnitureType.SINK).level(level1).furnitureTypeName("싱크대").build());

                furnitureRepository.save(Furniture.builder().furnitureName("오븐1").furnitureType(FurnitureType.OVEN).level(level1).furnitureTypeName("오븐").build());

                furnitureRepository.save(Furniture.builder().furnitureName("주방1").furnitureType(FurnitureType.LIVINGETC).level(level1).furnitureTypeName("기타").build());
                furnitureRepository.save(Furniture.builder().furnitureName("접시1").furnitureType(FurnitureType.LIVINGETC).level(level1).furnitureTypeName("기타").build());

                furnitureRepository.save(Furniture.builder().furnitureName("변기1").furnitureType(FurnitureType.TOILET).level(level1).furnitureTypeName("변기").build());

                furnitureRepository.save(Furniture.builder().furnitureName("욕조1").furnitureType(FurnitureType.BATH).level(level1).furnitureTypeName("욕조").build());

                furnitureRepository.save(Furniture.builder().furnitureName("수건걸이1").furnitureType(FurnitureType.TOILETETC).level(level1).furnitureTypeName("기타").build());
                furnitureRepository.save(Furniture.builder().furnitureName("휴지걸이1").furnitureType(FurnitureType.TOILETETC).level(level1).furnitureTypeName("기타").build());
                furnitureRepository.save(Furniture.builder().furnitureName("휴지1").furnitureType(FurnitureType.TOILETETC).level(level1).furnitureTypeName("기타").build());

                // 방에 들어가는 가구 카테고리 초기화
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.WALL).roomType(RoomType.PRIVATE).typeName("벽").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.FLOOR).roomType(RoomType.PRIVATE).typeName("바닥").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.DESK).roomType(RoomType.PRIVATE).typeName("책상").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.BED).roomType(RoomType.PRIVATE).typeName("침대").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.SOFA).roomType(RoomType.PRIVATE).typeName("소파").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.CLOSET).roomType(RoomType.PRIVATE).typeName("옷장").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.DRAWER).roomType(RoomType.PRIVATE).typeName("서랍장").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.BOOKSHELF).roomType(RoomType.PRIVATE).typeName("책장").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.CHAIR).roomType(RoomType.PRIVATE).typeName("의자").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.TRASHCAN).roomType(RoomType.PRIVATE).typeName("쓰레기통").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.DECORATION).roomType(RoomType.PRIVATE).typeName("장식품").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.LIGHT).roomType(RoomType.PRIVATE).typeName("조명").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.POKEMON).roomType(RoomType.PRIVATE).typeName("포켓몬").build());

                // KITCHEN 방에 들어가는 가구 타입
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.WALL).roomType(RoomType.KITCHEN).typeName("벽").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.FLOOR).roomType(RoomType.KITCHEN).typeName("바닥").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.DESK).roomType(RoomType.KITCHEN).typeName("책상").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.CHAIR).roomType(RoomType.KITCHEN).typeName("의자").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.REFRIGERATOR).roomType(RoomType.KITCHEN).typeName("냉장고").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.WASHINGMACHINE).roomType(RoomType.KITCHEN).typeName("세탁기").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.SINK).roomType(RoomType.KITCHEN).typeName("싱크대").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.OVEN).roomType(RoomType.KITCHEN).typeName("오븐").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.TRASHCAN).roomType(RoomType.KITCHEN).typeName("쓰레기통").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.DECORATION).roomType(RoomType.KITCHEN).typeName("장식품").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.LIGHT).roomType(RoomType.KITCHEN).typeName("조명").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.POKEMON).roomType(RoomType.KITCHEN).typeName("포켓몬").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.LIVINGETC).roomType(RoomType.KITCHEN).typeName("기타").build());

                // TOILET 방에 들어가는 가구 타입
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.WALL).roomType(RoomType.TOILET).typeName("벽").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.FLOOR).roomType(RoomType.TOILET).typeName("바닥").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.TRASHCAN).roomType(RoomType.TOILET).typeName("쓰레기통").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.DECORATION).roomType(RoomType.TOILET).typeName("장식품").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.LIGHT).roomType(RoomType.TOILET).typeName("조명").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.TOILET).roomType(RoomType.TOILET).typeName("변기").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.SINK).roomType(RoomType.TOILET).typeName("싱크대").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.BATH).roomType(RoomType.TOILET).typeName("욕조").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.POKEMON).roomType(RoomType.TOILET).typeName("포켓몬").build());
                roomFurnitureCategoryRepository.save(RoomFurnitureCategory.builder().furnitureType(FurnitureType.TOILETETC).roomType(RoomType.TOILET).typeName("기타").build());
            }
        };
    }
}
