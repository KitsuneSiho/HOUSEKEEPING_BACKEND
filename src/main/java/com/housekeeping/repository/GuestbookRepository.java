package com.housekeeping.repository;

import com.housekeeping.entity.Guestbook;
import com.housekeeping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {

    List<Guestbook> findByGuestbookOwner(User guestbookOwner);

    // 방 주인과 보관된 방명록을 조회하는 메서드
    List<Guestbook> findByGuestbookOwnerAndGuestbookIsArchived(User guestbookOwner, boolean guestbookIsArchived);
}
