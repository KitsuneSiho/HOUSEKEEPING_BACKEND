package com.housekeeping.service;

import com.housekeeping.DTO.UserSettingDTO;

public interface UserSettingService {
    UserSettingDTO getUserSettingsById(Long userId);
    void updateFoodNoticeSettingById(Long userId, boolean settingFoodNotice);
}