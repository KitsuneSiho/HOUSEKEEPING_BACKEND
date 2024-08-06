package com.housekeeping.repository;

import com.housekeeping.entity.LevelEXPTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelEXPTableRepository extends JpaRepository<LevelEXPTable, Long> {
    LevelEXPTable findByLevelLevel(int levelLevel);
}