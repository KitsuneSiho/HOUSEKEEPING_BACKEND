package com.housekeeping.entity;

import com.housekeeping.entity.enums.ChatRoomType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    private String chatRoomName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatRoomType chatRoomType;

    @Column(nullable = false)
    private LocalDateTime chatRoomCreatedAt = LocalDateTime.now();
}
