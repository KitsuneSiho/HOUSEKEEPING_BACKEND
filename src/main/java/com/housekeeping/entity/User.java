package com.housekeeping.entity;

import com.housekeeping.entity.enums.UserPlatform;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userUserName;

    @Column(nullable = false)
    private String userName;

    @Column(unique = true)
    private String userNickname;

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

    private String userPhone;

    @Lob
    private byte[] userImage;

    @Column(nullable = false)
    private boolean userIsOnline = false;
}
