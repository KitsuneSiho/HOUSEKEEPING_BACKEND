package com.housekeeping.repository;

import com.housekeeping.entity.Furniture;
import com.housekeeping.repository.custom.FurnitureRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FurnitureRepository extends JpaRepository<Furniture, Long>, FurnitureRepositoryCustom {
}
