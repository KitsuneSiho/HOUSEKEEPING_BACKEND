package com.housekeeping.entity;

import com.housekeeping.entity.enums.GuestbookColor;
import com.housekeeping.entity.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Guestbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestbookId;

    @ManyToOne
    @JoinColumn(name = "guestbookOwnerId", nullable = false)
    private User guestbookOwner;

    @ManyToOne
    @JoinColumn(name = "guestbookWriterId", nullable = false)
    private User guestbookWriter;

    @Column(nullable = false)
    private String guestbookContent;

    @Column(nullable = false)
    private boolean guestbookIsSecret = false;

    @Column(nullable = false)
    private boolean guestbookIsRead = false;

    @Column(nullable = false)
    private LocalDateTime guestbookTimestamp = LocalDateTime.now();

    @Column(nullable = false)
    private boolean guestbookIsArchived = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GuestbookColor guestbookColor;
}
