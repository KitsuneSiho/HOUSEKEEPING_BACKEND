package com.housekeeping.repository;

import com.housekeeping.entity.MessageReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageReadStatusRepository extends JpaRepository<MessageReadStatus, Long> {

    // 메시지와 회원 번호를 기준으로 메시지 읽음 상태를 반환함
    @Query("SELECT status FROM MessageReadStatus status where status.message.messageId = :messageId and status.user.userId = :userId")
    MessageReadStatus findByMessageIdAndUserId(@Param("messageId") Long messageId, @Param("userId") Long userId);

    // 특정 유저가 특정 채팅방에서 읽지 않은 메시지의 수를 출력함
    @Query("SELECT COUNT(mrs) FROM MessageReadStatus mrs JOIN mrs.message m WHERE m.chatRoom.chatRoomId = :chatRoomId " +
            "AND mrs.user.userId = :userId AND mrs.isRead = false")
    Long countUnreadMessagesByChatRoomIdAndUserId(@Param("chatRoomId") Long chatRoomId, @Param("userId") Long userId);

    // 특정 채팅 방에서 특정 유저가 읽지 않은 메시지의 번호 리스트를 반환함
    @Query("SELECT mrs.readStatusId FROM MessageReadStatus mrs JOIN mrs.message m WHERE m.chatRoom.chatRoomId = :chatRoomId " +
            "AND mrs.user.userId = :userId AND mrs.isRead = false")
    List<Long> findUnreadMessageIdsByChatRoomIdAndUserId(@Param("chatRoomId") Long chatRoomId, @Param("userId") Long userId);

}
