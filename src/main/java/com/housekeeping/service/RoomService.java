package com.housekeeping.service;

import com.housekeeping.DTO.RoomDTO;
import com.housekeeping.DTO.ScheduleDTO;
import com.housekeeping.entity.Room;
import com.housekeeping.repository.RoomRepository;
import com.housekeeping.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    // 유저 ID로 방과 스케줄 정보를 가져오는 메서드
    public List<RoomDTO> getRoomDetailsByUserId(Long userId) {
        // 유저 아이디로 방 정보를 우선 가져옴
        List<Room> rooms = roomRepository.findByUserUserId(userId);

        return rooms.stream().map(room -> {
            // 방 ID로 스케줄을 가져옵니다.
            List<ScheduleDTO> schedules = scheduleRepository.findByRoom_RoomId(room.getRoomId()).stream()
                    .map(schedule -> new ScheduleDTO(
                            schedule.getScheduleId(),
                            schedule.getRoom().getRoomId(),
                            schedule.getScheduleName(),
                            schedule.getScheduleDetail(),
                            schedule.getScheduleDate(),
                            schedule.isScheduleIsChecked(),
                            schedule.isScheduleIsAlarm()
                    ))
                    .collect(Collectors.toList());

            return RoomDTO.builder()
                    .roomId(room.getRoomId())
                    .roomName(room.getRoomName())
                    .roomType(room.getRoomType())
                    .roomPollution(room.getRoomPollution())
                    .schedules(schedules)
                    .build();
        }).collect(Collectors.toList());
    }

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