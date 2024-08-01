package com.housekeeping.service;

import com.housekeeping.DTO.RoomDTO;
import com.housekeeping.entity.Room;
import com.housekeeping.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomDTO> getRoomNamesByIds(List<Long> roomIds) {
        List<Room> rooms = roomRepository.findByRoomIdIn(roomIds);
        return rooms.stream()
                .map(room -> RoomDTO.builder()
                        .roomId(room.getRoomId())
                        .roomName(room.getRoomName())
                        .build())
                .collect(Collectors.toList());
    }
}