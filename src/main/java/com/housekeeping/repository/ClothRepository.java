package com.housekeeping.repository;

import com.housekeeping.DTO.ClothDTO;
import com.housekeeping.entity.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothRepository extends JpaRepository<Cloth, Long> {
    List<Cloth> findByUserUserId(Long userId);


}
