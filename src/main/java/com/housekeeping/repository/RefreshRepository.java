package com.housekeeping.repository;

import com.housekeeping.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {
    List<RefreshEntity> findByUsername(String username);
    Boolean existsByRefresh(String refresh);
    @Transactional
    void deleteByRefresh(String refresh);
}
