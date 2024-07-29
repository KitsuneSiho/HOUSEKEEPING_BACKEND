package com.housekeeping.repository;

import com.housekeeping.entity.ChatRoomMember;
import com.housekeeping.repository.custom.ChatRoomMemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long>, ChatRoomMemberRepositoryCustom {
}
