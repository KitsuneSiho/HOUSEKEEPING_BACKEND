package com.housekeeping.DTO;

import com.housekeeping.entity.enums.ChatRoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {

    private Long chatRoomId;
    private String chatRoomName;
    private ChatRoomType chatRoomType;
    private LocalDateTime chatRoomUpdatedAt;
    private String recentMessage;
    private List<Long> userIdList;
    private List<String> nickNameList;
    private Long unreadMessageCount;
}
