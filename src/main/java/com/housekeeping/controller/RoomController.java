package com.housekeeping.controller;

import com.housekeeping.DTO.RoomColorDTO;
import com.housekeeping.DTO.RoomDTO;
import com.housekeeping.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/names")
    public List<RoomDTO> getRoomNames(@RequestBody List<Long> roomIds) {
        return roomService.getRoomNamesByIds(roomIds);
    }

    // 방 정보를 저장/업데이트
    @PostMapping("/register")
    public RoomColorDTO registerRoom(@RequestBody RoomColorDTO roomColorDTO) {
        return roomService.saveRoom(roomColorDTO);
    }

    // 유저가 가진 방 정보들을 반환
    @GetMapping("/list")
    public List<RoomColorDTO> getAllRooms(@RequestParam("userId") Long userId) {
        return roomService.getRoomColorDTOsByUserId(userId);
    }
}
