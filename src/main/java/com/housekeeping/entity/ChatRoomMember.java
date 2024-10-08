package com.housekeeping.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_room_member", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"chatRoomId", "userId"})
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomMemberId;

    @ManyToOne
    @JoinColumn(name = "chatRoomId", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
