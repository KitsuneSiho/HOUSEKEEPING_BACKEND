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
public class MessageReadStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readStatusId;

    @ManyToOne
    @JoinColumn(name = "messageId", nullable = false)
    private Message message;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private boolean isRead = false;

    @Column
    private LocalDateTime readTimestamp;
}
