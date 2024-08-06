package com.housekeeping.DTO;

import com.housekeeping.entity.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomColorDTO {
    private Long roomId;
    private Long userId;
    private String roomName;
    private RoomType roomType;
    private int roomPollution;
    private String roomWallsColor;

}
