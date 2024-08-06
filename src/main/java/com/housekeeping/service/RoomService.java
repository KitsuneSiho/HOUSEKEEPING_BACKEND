package com.housekeeping.service;

import com.housekeeping.DTO.RoomColorDTO;
import com.housekeeping.DTO.RoomDTO;
import com.housekeeping.entity.Room;
import com.housekeeping.entity.UserEntity;
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

    // 유저 번호를 기준으로 방DTO를 반환
    public List<RoomColorDTO> getRoomColorDTOsByUserId(Long userId) {

        return roomRepository.getRoomColorDTOsByUserId(userId);
    }

    // 방 정보를 업데이트 / 등록함
    public RoomColorDTO saveRoom(RoomColorDTO roomColorDTO) {

        Room room = Room.builder()
                .roomId(roomColorDTO.getRoomId())
                .user(UserEntity.builder().userId(roomColorDTO.getUserId()).build())
                .roomName(roomColorDTO.getRoomName())
                .roomType(roomColorDTO.getRoomType())
                .roomPollution(roomColorDTO.getRoomPollution())
                .roomWallsColor(roomColorDTO.getRoomWallsColor())
                .build();

        Room result = roomRepository.save(room);

        roomColorDTO.setRoomId(result.getRoomId());

        return roomColorDTO;
    }

}