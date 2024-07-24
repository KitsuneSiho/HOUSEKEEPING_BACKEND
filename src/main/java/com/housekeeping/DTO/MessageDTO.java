package com.housekeeping.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private Long messageId;
    private Long messageSenderId;
    private String messageSenderNickname;
    private Long chatRoomId;
    private String messageContent;
    private LocalDateTime messageTimestamp;

}
