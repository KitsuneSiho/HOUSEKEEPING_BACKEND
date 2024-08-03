package com.housekeeping.service;

import com.housekeeping.DTO.FurnitureDTO;

import java.util.List;

public interface FurnitureService {

    List<FurnitureDTO> getFurnitureList(int level);
}
