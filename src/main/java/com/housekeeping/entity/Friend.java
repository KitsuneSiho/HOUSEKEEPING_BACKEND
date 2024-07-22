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
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @ManyToOne
    @JoinColumn(name = "friendUserId1", nullable = false)
    private User friendUser1;

    @ManyToOne
    @JoinColumn(name = "friendUserId2", nullable = false)
    private User friendUser2;

    @Column(nullable = false)
    private LocalDateTime friendDate = LocalDateTime.now();

    // Getters and Setters
}
