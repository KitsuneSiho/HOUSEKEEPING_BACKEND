package com.housekeeping.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;  // 이 줄을 추가합니다.

    @Column(nullable = false, unique = true)
    private String refresh;

    @Column(nullable = false)
    private LocalDateTime expiration;
}