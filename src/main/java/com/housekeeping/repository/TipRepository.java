package com.housekeeping.repository;

import com.housekeeping.entity.Tip;
import com.housekeeping.repository.custom.TipRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipRepository extends JpaRepository<Tip, Long>, TipRepositoryCustom {

}
