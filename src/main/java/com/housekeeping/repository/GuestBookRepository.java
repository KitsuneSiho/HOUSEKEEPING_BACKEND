package com.housekeeping.repository;

import com.housekeeping.entity.Guestbook;
import com.housekeeping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestBookRepository extends JpaRepository<Guestbook, Long> {
    List<Guestbook> findByGuestbookOwner(User guestbookOwner);
}
