package com.housekeeping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private LocalDateTime guestbookTimestamp = LocalDateTime.now();

    // Getters and Setters
}
