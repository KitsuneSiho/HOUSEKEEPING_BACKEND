package com.housekeeping.repository;

import com.housekeeping.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestBookRepository extends JpaRepository<Guestbook, Long> {
}
