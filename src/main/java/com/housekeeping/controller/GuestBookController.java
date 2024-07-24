package com.housekeeping.controller;

import com.housekeeping.entity.Guestbook;
import com.housekeeping.entity.User;
import com.housekeeping.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guestbook")
public class GuestBookController {

    private final GuestBookService guestbookService;

    // 방 주인의 방명록 조회
    @GetMapping("/list/{ownerId}")
    public List<Guestbook> guestbookListByOwner(@PathVariable Long ownerId) {
        User guestbookOwner = new User();
        guestbookOwner.setUserId(ownerId);
        return guestbookService.findByGuestbookOwner(guestbookOwner);
    }

    // 방명록 등록
    @PostMapping("/write")
    public Guestbook guestbookWrite(@RequestBody Guestbook newEntry) {
        return guestbookService.save(newEntry);
    }

    // 방명록 삭제
    @DeleteMapping("/delete/{id}")
    public void guestbookDelete(@PathVariable Long id) {
        guestbookService.deleteById(id);
    }
}