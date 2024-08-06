package com.housekeeping.repository;

import com.housekeeping.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {
    void deleteByNickname(String nickname);
    Boolean existsByRefresh(String refresh);
    void deleteByRefresh(String refresh);
    Optional<RefreshEntity> findByNickname(String nickname);
}