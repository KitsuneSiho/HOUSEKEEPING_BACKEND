package com.housekeeping.entity;

import com.housekeeping.entity.enums.RequestStatus;
import com.housekeeping.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "requestSenderId", nullable = false)
    private User requestSender;

    @ManyToOne
    @JoinColumn(name = "requestReceiverId", nullable = false)
    private User requestReceiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus requestStatus = RequestStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime requestDate = LocalDateTime.now();
}
