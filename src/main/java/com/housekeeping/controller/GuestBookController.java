package com.housekeeping.controller;

import com.housekeeping.DTO.GuestbookDTO;
import com.housekeeping.entity.User;
import com.housekeeping.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guestbook")
public class GuestBookController {

    private final GuestbookService guestbookService;

    // 방 주인의 방명록 조회
    @GetMapping("/list/{ownerId}")
    public List<GuestbookDTO> guestbookListByOwner(@PathVariable("ownerId") Long ownerId) {
        User guestbookOwner = new User();
        guestbookOwner.setUserId(ownerId);
        return guestbookService.findByGuestbookOwner(guestbookOwner);
    }

    // 방명록 등록
    @PostMapping("/write")
    public GuestbookDTO guestbookWrite(@RequestBody GuestbookDTO newEntry) {
        return guestbookService.save(newEntry);
    }

    // 방명록 삭제
    @DeleteMapping("/delete/{id}")
    public void guestbookDelete(@PathVariable("id") Long id) {
        guestbookService.deleteById(id);
    }

    // 방명록 보관
    @PatchMapping("/archive/{id}")
    public void guestbookArchive(@PathVariable("id") Long id) {
        guestbookService.archiveById(id);
    }

    // 특정 방 주인의 보관된 방명록 조회
    @GetMapping("/archived/{ownerId}")
    public List<GuestbookDTO> getArchivedGuestbooksByOwner(@PathVariable("ownerId") Long ownerId) {
        User guestbookOwner = new User();
        guestbookOwner.setUserId(ownerId);
        return guestbookService.findArchivedGuestbooksByOwner(guestbookOwner);
    }
}
