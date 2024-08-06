package com.housekeeping.mapper;

import com.housekeeping.DTO.GuestbookDTO;
import com.housekeeping.entity.Guestbook;
import com.housekeeping.entity.UserEntity;

public class GuestbookMapper {

    public static GuestbookDTO toDTO(Guestbook guestbook) {
        return GuestbookDTO.builder()
                .guestbookId(guestbook.getGuestbookId())
                .guestbookOwnerId(guestbook.getGuestbookOwner().getUserId())
                .guestbookWriterId(guestbook.getGuestbookWriter().getUserId())
                .writerNickname(guestbook.getGuestbookWriter().getNickname()) // 작성자 닉네임 추가
                .ownerNickname(guestbook.getGuestbookOwner().getNickname()) // 방명록 주인 닉네임 추가
                .guestbookContent(guestbook.getGuestbookContent())
                .guestbookIsSecret(guestbook.isGuestbookIsSecret())
                .guestbookIsRead(guestbook.isGuestbookIsRead())
                .guestbookTimestamp(guestbook.getGuestbookTimestamp())
                .guestbookColor(guestbook.getGuestbookColor()) // 색상 추가
                .guestbookIsArchived(guestbook.isGuestbookIsArchived()) // 보관 여부 추가
                .build();
    }

    public static Guestbook toEntity(GuestbookDTO guestDTO, UserEntity guestbookOwner, UserEntity guestbookWriter) {
        return Guestbook.builder()
                .guestbookId(guestDTO.getGuestbookId())
                .guestbookOwner(guestbookOwner)
                .guestbookWriter(guestbookWriter)
                .guestbookContent(guestDTO.getGuestbookContent())
                .guestbookIsSecret(guestDTO.isGuestbookIsSecret())
                .guestbookIsRead(guestDTO.isGuestbookIsRead())
                .guestbookTimestamp(guestDTO.getGuestbookTimestamp())
                .guestbookColor(guestDTO.getGuestbookColor()) // 색상 추가
                .guestbookIsArchived(guestDTO.isGuestbookIsArchived()) // 보관 여부 추가
                .build();
    }
}
