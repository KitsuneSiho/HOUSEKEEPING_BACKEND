package com.housekeeping.service.implement;

import com.housekeeping.DTO.UserSettingDTO;
import com.housekeeping.entity.User;
import com.housekeeping.entity.UserSettings;
import com.housekeeping.repository.UserRepository;
import com.housekeeping.repository.UserSettingRepository;
import com.housekeeping.service.UserSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserSettingServiceImpl implements UserSettingService {

    private static final Logger logger = LoggerFactory.getLogger(UserSettingServiceImpl.class);

    private final UserSettingRepository userSettingRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserSettingServiceImpl(UserSettingRepository userSettingRepository, UserRepository userRepository) {
        this.userSettingRepository = userSettingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserSettingDTO getUserSettingsById(Long userId) {
        logger.info("Searching for user settings with userId: {}", userId);
        UserSettings userSettings = userSettingRepository.findById(userId)
                .orElseGet(() -> createDefaultUserSettings(userId));
        logger.info("User settings found or created: {}", userSettings);
        return convertToDTO(userSettings);
    }

    @Override
    public void updateFoodNoticeSettingById(Long userId, boolean settingFoodNotice) {
        logger.info("Updating food notice setting for userId: {} to {}", userId, settingFoodNotice);
        UserSettings userSettings = userSettingRepository.findById(userId)
                .orElseGet(() -> createDefaultUserSettings(userId));
        userSettings.setSettingFoodNotice(settingFoodNotice);
        userSettingRepository.save(userSettings);
        logger.info("Food notice setting updated successfully");
    }

    private UserSettings createDefaultUserSettings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));

        UserSettings defaultSettings = new UserSettings();
        defaultSettings.setUser(user);
        defaultSettings.setSettingCheckNotice(false);
        defaultSettings.setSettingWarnNotice(false);
        defaultSettings.setSettingFoodNotice(false);
        defaultSettings.setSettingSearchAgree(false);
        defaultSettings.setSettingIsOfflineMode(false);

        return userSettingRepository.save(defaultSettings);
    }

    private UserSettingDTO convertToDTO(UserSettings userSettings) {
        return UserSettingDTO.builder()
                .userId(userSettings.getUserId())
                .settingCheckNotice(userSettings.isSettingCheckNotice())
                .settingFoodNotice(userSettings.isSettingFoodNotice())
                .settingIsOfflineMode(userSettings.isSettingIsOfflineMode())
                .settingSearchAgree(userSettings.isSettingSearchAgree())
                .settingWarnNotice(userSettings.isSettingWarnNotice())
                .build();
    }
}