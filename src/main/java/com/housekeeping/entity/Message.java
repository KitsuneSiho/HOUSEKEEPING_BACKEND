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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "messageSenderId", nullable = false)
    private User messageSender;

    @ManyToOne
    @JoinColumn(name = "messageReceiverId", nullable = false)
    private User messageReceiver;

    @Column(nullable = false)
    private String messageContent;

    @Column(nullable = false)
    private LocalDateTime messageTimestamp = LocalDateTime.now();

    @Column(nullable = false)
    private boolean messageIsRead = false;

    // Getters and Setters
}
