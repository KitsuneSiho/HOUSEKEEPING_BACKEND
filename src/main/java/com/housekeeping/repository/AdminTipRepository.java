package com.housekeeping.repository;

import com.housekeeping.entity.AdminTip;
import com.housekeeping.entity.enums.TipCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminTipRepository extends JpaRepository<AdminTip, Long> {
    List<AdminTip> findAllByOrderByCreatedAtDesc();
    List<AdminTip> findByCategoryOrderByCreatedAtDesc(TipCategory category);
}