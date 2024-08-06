package com.housekeeping.service;

import com.housekeeping.DTO.FurnitureDTO;
import com.housekeeping.DTO.FurnitureTypeDTO;
import com.housekeeping.entity.enums.FurnitureType;

import java.util.List;

public interface FurnitureService {

    // 현재 레벨에서 사용 가능한 가구 목록을 반환
    List<FurnitureDTO> getFurnitureList(int level);

    // 현재 레벨에서 사용 가능한 가구 타입과 이름 목록을 반환
    List<FurnitureTypeDTO> getFurnitureTypeList(int level);
}
