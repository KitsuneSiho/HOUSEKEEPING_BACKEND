package com.housekeeping.entity;

import com.housekeeping.entity.user.UserEntity;
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
    private UserEntity guestbookOwner;

    @ManyToOne
    @JoinColumn(name = "guestbookWriterId", nullable = false)
    private UserEntity guestbookWriter;

    @Column(nullable = false)
    private String guestbookContent;

    @Column(nullable = false)
    private boolean guestbookIsSecret = false;

    @Column(nullable = false)
    private boolean guestbookIsRead = false;

    @Column(nullable = false)
    private LocalDateTime guestbookTimestamp = LocalDateTime.now();
}
