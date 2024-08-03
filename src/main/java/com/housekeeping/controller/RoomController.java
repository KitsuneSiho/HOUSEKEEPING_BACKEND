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

    @PostMapping("/names")
    public List<RoomDTO> getRoomNames(@RequestBody List<Long> roomIds) {
        return roomService.getRoomNamesByIds(roomIds);
    }
}
