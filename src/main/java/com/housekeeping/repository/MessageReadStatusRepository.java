package com.housekeeping.repository;

import com.housekeeping.entity.MessageReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageReadStatusRepository extends JpaRepository<MessageReadStatus, Long> {

    @Query("SELECT status FROM MessageReadStatus status where status.message.messageId = :messageId and status.user.userId = :userId")
    MessageReadStatus findByMessageIdAndUserId(@Param("messageId") Long messageId, @Param("userId") Long userId);

    @Query("SELECT COUNT(mrs) FROM MessageReadStatus mrs JOIN mrs.message m WHERE m.chatRoom.chatRoomId = :chatRoomId " +
            "AND mrs.user.userId = :userId AND mrs.isRead = false")
    Long countUnreadMessagesByChatRoomIdAndUserId(@Param("chatRoomId") Long chatRoomId, @Param("userId") Long userId);

    @Query("SELECT mrs.readStatusId FROM MessageReadStatus mrs JOIN mrs.message m WHERE m.chatRoom.chatRoomId = :chatRoomId " +
            "AND mrs.user.userId = :userId AND mrs.isRead = false")
    List<Long> findUnreadMessageIdsByChatRoomIdAndUserId(@Param("chatRoomId") Long chatRoomId, @Param("userId") Long userId);

}
