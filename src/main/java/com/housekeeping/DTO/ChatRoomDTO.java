package com.housekeeping.DTO;

import com.housekeeping.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {

    private ChatRoom chatRoom;
    private List<Long> userIdList;
    private List<String> nickNameList;
    private Long unreadMessageCount;
}
