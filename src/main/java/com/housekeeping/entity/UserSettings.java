package com.housekeeping.entity;


import com.housekeeping.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private User user;

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
