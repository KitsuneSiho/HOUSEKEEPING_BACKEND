package com.housekeeping.config;

import com.housekeeping.entity.LevelEXPTable;
import com.housekeeping.repository.LevelEXPTableRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class LevelInitializer {

    @Bean
    @Order(1)
    public CommandLineRunner initializeLevels(LevelEXPTableRepository levelRepository) {
        return args -> {
            if (levelRepository.count() == 0) {
                // 기본 레벨 데이터 생성
                LevelEXPTable level1 = new LevelEXPTable();
                level1.setLevelLevel(1);
                level1.setLevelName("자린이");
                level1.setLevelRequireEXP(0);
                levelRepository.save(level1);

                LevelEXPTable level2 = new LevelEXPTable();
                level2.setLevelLevel(2);
                level2.setLevelName("숙달된 조교");
                level2.setLevelRequireEXP(100);
                levelRepository.save(level2);

                LevelEXPTable level3 = new LevelEXPTable();
                level3.setLevelLevel(3);
                level3.setLevelName("자취왕");
                level3.setLevelRequireEXP(300);
                levelRepository.save(level3);

                LevelEXPTable level4 = new LevelEXPTable();
                level4.setLevelLevel(4);
                level4.setLevelName("자취마스터");
                level4.setLevelRequireEXP(600);
                levelRepository.save(level4);

                LevelEXPTable level5 = new LevelEXPTable();
                level5.setLevelLevel(5);
                level5.setLevelName("자취의 신");
                level5.setLevelRequireEXP(1000);
                levelRepository.save(level5);

                System.out.println("기본 레벨 데이터가 생성되었습니다.");
            } else {
                System.out.println("이미 레벨 데이터가 존재합니다.");
            }
        };
    }
}

