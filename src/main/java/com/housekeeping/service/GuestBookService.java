package com.housekeeping.service;

import com.housekeeping.entity.Guestbook;
import com.housekeeping.entity.User;
import com.housekeeping.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestBookService {
    private final GuestBookRepository guestbookRepository;

    public List<Guestbook> findByGuestbookOwner(User guestbookOwner) {
        return guestbookRepository.findByGuestbookOwner(guestbookOwner);
    }

    public Guestbook save(Guestbook guestbook) {
        return guestbookRepository.save(guestbook);
    }

    public void deleteById(Long id) {
        guestbookRepository.deleteById(id);
    }
}