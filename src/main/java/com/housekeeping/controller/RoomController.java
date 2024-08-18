package com.housekeeping.controller;

import com.housekeeping.DTO.RoomColorDTO;
import com.housekeeping.DTO.RoomDTO;
import com.housekeeping.DTO.RoomNameUpdateDTO;
import com.housekeeping.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // 유저 ID로 방과 스케줄 정보를 반환하는 엔드포인트
    @PostMapping("/details")
    public List<RoomDTO> getRoomDetailsByUserId(@RequestBody Long userId) {
        return roomService.getRoomDetailsByUserId(userId);
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

    // 방 이름 수정
    @PostMapping("/rename")
    public ResponseEntity<String> renameRoom(@RequestParam("roomId") Long roomId, @RequestParam("newName") String newName) {
        roomService.renameRoom(roomId, newName);
        return ResponseEntity.ok("Room name updated successfully");
    }

    // 방 오염도 받기
    @GetMapping("/getPollution")
    public ResponseEntity<List<RoomDTO>> getRoomPollution(@RequestParam List<Long> roomIds) {
        List<RoomDTO> pollutionData = roomService.getRoomPollution(roomIds);
        return ResponseEntity.ok(pollutionData);
    }
    // 방 오염도 업데이트
    @PatchMapping("/updatePollution")
    public ResponseEntity<?> updateRoomPollution(@RequestBody Map<String, Object> payload) {
        Long roomId = ((Number) payload.get("roomId")).longValue();
        int pollutionValue = (int) payload.get("pollution");

        roomService.updateRoomPollution(roomId, pollutionValue);
        return ResponseEntity.ok().build();
    }


}
