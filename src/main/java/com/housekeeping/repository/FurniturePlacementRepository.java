package com.housekeeping.repository;

import com.housekeeping.entity.FurniturePlacement;
import com.housekeeping.repository.custom.FurniturePlacementRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FurniturePlacementRepository extends JpaRepository<FurniturePlacement, Long>, FurniturePlacementRepositoryCustom {
}
