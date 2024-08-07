package com.housekeeping.repository.custom;

import com.housekeeping.DTO.RoomColorDTO;

import java.util.List;

public interface RoomRepositoryCustom {

    List<RoomColorDTO> getRoomColorDTOsByUserId(Long userId);
}
