package com.housekeeping.entity;

import com.housekeeping.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "chatRoomId", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "messageSenderId", nullable = false)
    private User messageSender;

    @Column(nullable = false)
    private String messageContent;

    @Column(nullable = false)
    private LocalDateTime messageTimestamp = LocalDateTime.now();

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<MessageReadStatus> messageReadStatuses;
}
