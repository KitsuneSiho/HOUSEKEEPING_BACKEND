package com.housekeeping.DTO;

import com.housekeeping.entity.enums.GuestbookColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GuestbookDTO {
    private Long guestbookId;
    private Long guestbookOwnerId;
    private Long guestbookWriterId;
    private String ownerNickname;
    private String writerNickname;
    private String guestbookContent;
    private boolean guestbookIsSecret;
    private boolean guestbookIsRead;
    private LocalDateTime guestbookTimestamp;
    private GuestbookColor guestbookColor;
    private boolean guestbookIsArchived;
}
