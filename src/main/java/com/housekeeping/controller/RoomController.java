package com.housekeeping.controller;

import com.housekeeping.DTO.RoomDTO;
import com.housekeeping.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    // 유저 ID로 방과 스케줄 정보를 반환하는 엔드포인트
    @PostMapping("/details")
    public List<RoomDTO> getRoomDetailsByUserId(@RequestBody Long userId) {
        return roomService.getRoomDetailsByUserId(userId);
    }
}
