package com.housekeeping.repository.custom;

import com.housekeeping.DTO.FurnitureDTO;

import java.util.List;

public interface FurnitureRepositoryCustom {

    List<FurnitureDTO> getFurnitureList (int level);
}
