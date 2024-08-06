package com.housekeeping.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSettings {
    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private UserEntity user;

    @Column(nullable = false)
    private boolean settingCheckNotice = true;

    @Column(nullable = false)
    private boolean settingWarnNotice = true;

    @Column(nullable = false)
    private boolean settingFoodNotice = true;

    @Column(nullable = false)
    private boolean settingSearchAgree = true;

    @Column(nullable = false)
    private boolean settingIsOfflineMode = false;
}
