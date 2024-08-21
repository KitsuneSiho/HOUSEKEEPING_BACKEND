package com.housekeeping.service;

import com.housekeeping.DTO.RoomColorDTO;
import com.housekeeping.DTO.RoomDTO;
import com.housekeeping.DTO.ScheduleDTO;
import com.housekeeping.entity.Room;
import com.housekeeping.entity.Schedule;
import com.housekeeping.entity.User;
import com.housekeeping.repository.RoomRepository;
import com.housekeeping.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                    .map(schedule -> {
                        // Routine이 null일 수 있는 경우를 처리
                        Long routineId = (schedule.getRoutine() != null) ? schedule.getRoutine().getRoutineId() : null;

                        return new ScheduleDTO(
                                schedule.getScheduleId(),
                                schedule.getRoom().getRoomId(),
                                schedule.getScheduleName(),
                                schedule.getScheduleDetail(),
                                schedule.getScheduleDate(),
                                schedule.isScheduleIsChecked(),
                                schedule.isScheduleIsAlarm(),
                                routineId // Routine이 null인 경우, routineId도 null로 설정
                        );
                    })
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

    // 유저 번호를 기준으로 방DTO를 반환
    public List<RoomColorDTO> getRoomColorDTOsByUserId(Long userId) {

        return roomRepository.getRoomColorDTOsByUserId(userId);
    }

    // 방 정보를 업데이트 / 등록함
    public RoomColorDTO saveRoom(RoomColorDTO roomColorDTO) {

        Room room = Room.builder()
                .roomId(roomColorDTO.getRoomId())
                .user(User.builder().userId(roomColorDTO.getUserId()).build())
                .roomName(roomColorDTO.getRoomName())
                .roomType(roomColorDTO.getRoomType())
                .roomPollution(roomColorDTO.getRoomPollution())
                .roomWallsColor(roomColorDTO.getRoomWallsColor())
                .build();

        Room result = roomRepository.save(room);

        roomColorDTO.setRoomId(result.getRoomId());

        return roomColorDTO;
    }

    public void renameRoom(Long roomId, String newName) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

        room.setRoomName(newName);
        roomRepository.save(room);
    }

    @Transactional
    public void updateRoomPollution(Long roomId, int pollution) {
        roomRepository.updateRoomPollution(roomId, pollution);
    }

}
