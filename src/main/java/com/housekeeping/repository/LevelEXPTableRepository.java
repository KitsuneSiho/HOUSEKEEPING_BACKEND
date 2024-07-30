package com.housekeeping.repository;

import com.housekeeping.entity.LevelEXPTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelEXPTableRepository extends JpaRepository<LevelEXPTable, Long> {
    // 필요한 쿼리 메서드를 추가하세요.
}
