package com.housekeeping.DTO;

import com.housekeeping.entity.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDTO {

    private Long requestId;
    private Long requestSenderId;
    private String senderNickname;
    private Long requestReceiverId;
    private RequestStatus requestStatus;
    private LocalDateTime requestDate;

}
