package com.housekeeping.DTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomNameUpdateDTO {
    private Long roomId;
    private String newName;
}
