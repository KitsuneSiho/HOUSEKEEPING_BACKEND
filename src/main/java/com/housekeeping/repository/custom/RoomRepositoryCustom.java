package com.housekeeping.repository.custom;

import com.housekeeping.DTO.RoomDTO;

import java.util.List;

public interface RoomRepositoryCustom {

    List<RoomDTO> getRoomDTOsByUserId(Long userId);
}
