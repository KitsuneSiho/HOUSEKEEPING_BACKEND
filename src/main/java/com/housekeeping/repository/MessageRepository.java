package com.housekeeping.repository;

import com.housekeeping.entity.Message;
import com.housekeeping.repository.custom.MessageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
}
