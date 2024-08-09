package com.housekeeping.repository;

import com.housekeeping.entity.LevelEXPTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelEXPTableRepository extends JpaRepository<LevelEXPTable, Long> {
    Optional<LevelEXPTable> findByLevelLevel(int levelLevel);
    Optional<LevelEXPTable> findByLevelName(String levelName);
}