package com.housekeeping.entity;

import com.housekeeping.entity.enums.Role;
import com.housekeeping.entity.enums.UserPlatform;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email", "userPlatform"}),
                @UniqueConstraint(columnNames = {"nickname"})
        })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserPlatform userPlatform;

    @Column(nullable = false)
    private LocalDateTime userEnrollment = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "levelId", nullable = false)
    private LevelEXPTable level;

    @Column(nullable = false)
    private int userEXP = 0;

    @Lob
    private byte[] userImage;

    @Column(nullable = false)
    private boolean userIsOnline = false;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}