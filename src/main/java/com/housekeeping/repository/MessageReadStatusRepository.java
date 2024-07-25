package com.housekeeping.repository;

import com.housekeeping.entity.MessageReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageReadStatusRepository extends JpaRepository<MessageReadStatus, Long> {
}
