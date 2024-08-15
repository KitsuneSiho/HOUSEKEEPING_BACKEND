package com.housekeeping.repository;

import com.housekeeping.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {
    Optional<RefreshEntity> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    Boolean existsByUserId(Long userId);
    Boolean existsByRefresh(String refresh);
}