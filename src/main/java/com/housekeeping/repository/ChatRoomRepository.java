package com.housekeeping.repository;

import com.housekeeping.entity.ChatRoom;
import com.housekeeping.repository.custom.ChatRoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {
}
