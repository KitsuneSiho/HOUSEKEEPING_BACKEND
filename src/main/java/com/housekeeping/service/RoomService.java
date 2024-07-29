package com.housekeeping.service;

import com.housekeeping.entity.Room;
import com.housekeeping.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> getRoomsByIds(List<Long> roomIds) {
        return roomRepository.findAllById(roomIds);
    }
}