package com.housekeeping.repository;

import com.housekeeping.DTO.ClothDTO;
import com.housekeeping.entity.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothRepository extends JpaRepository<Cloth, Long> {
    List<Cloth> findByUserUserId(Long userId);

    @Query("SELECT c FROM Cloth c WHERE c.user.userId = :userId AND " +
            "(:name IS NULL OR c.clothName = :name) AND " +
            "(:category IS NULL OR c.clothType IN :category) AND " +
            "(:details IS NULL OR c.clothCustomTag LIKE %:details%)")
    List<Cloth> findByUserUserIdAndFilters(@Param("userId") Long userId,
                                           @Param("name") String name,
                                           @Param("category") List<String> category,
                                           @Param("details") String details);
}
