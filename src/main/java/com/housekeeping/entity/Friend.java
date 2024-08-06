package com.housekeeping.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friend", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"friendUserId1", "friendUserId2"})
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @ManyToOne
    @JoinColumn(name = "friendUserId1", nullable = false)
    private UserEntity friendUser1;

    @ManyToOne
    @JoinColumn(name = "friendUserId2", nullable = false)
    private UserEntity friendUser2;

    @Column(nullable = false)
    private LocalDateTime friendDate = LocalDateTime.now();
}
