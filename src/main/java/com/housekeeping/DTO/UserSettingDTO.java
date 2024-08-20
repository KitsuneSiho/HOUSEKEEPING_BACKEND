package com.housekeeping.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingDTO {
    private Long userId;
    private boolean settingCheckNotice;
    private boolean settingWarnNotice;
    private boolean settingFoodNotice;
    private boolean settingSearchAgree;
    private boolean settingIsOfflineMode;
}
