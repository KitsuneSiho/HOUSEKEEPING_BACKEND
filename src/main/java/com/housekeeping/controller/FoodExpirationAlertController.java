package com.housekeeping.controller;

import com.housekeeping.entity.Food;
import com.housekeeping.entity.User;
import com.housekeeping.entity.UserSettings;
import com.housekeeping.repository.UserSettingRepository;
import com.housekeeping.service.FoodService;
import com.housekeeping.service.UserService;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@RestController
public class FoodExpirationAlertController {

    private static final Logger logger = LoggerFactory.getLogger(FoodExpirationAlertController.class);

    private final FoodService foodService;
    private final UserService userService;
    private final UserSettingRepository userSettingRepository;
    private final DefaultMessageService messageService;

    @Autowired
    public FoodExpirationAlertController(FoodService foodService,
                                         UserService userService,
                                         UserSettingRepository userSettingRepository,
                                         @Value("${nurigo.api.key}") String apiKey,
                                         @Value("${nurigo.api.secret}") String apiSecret) {
        this.foodService = foodService;
        this.userService = userService;
        this.userSettingRepository = userSettingRepository;
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    @Scheduled(cron = "0 30 19 * * ?")
    public void checkFoodExpirationAndSendAlerts() {
        logger.info("checkFoodExpirationAndSendAlerts method started.");
        LocalDate today = LocalDate.now();
        LocalDate fiveDaysLater = today.plusDays(5);

        logger.info("Today: {}, Five days later: {}", today, fiveDaysLater);

        List<Food> expiringFoods = foodService.findFoodsExpiringBetween(today, fiveDaysLater);
        logger.info("Expiring foods: {}", expiringFoods);

        for (Food food : expiringFoods) {
            User user = food.getUser();
            if (user != null && user.getPhoneNumber() != null) {
                UserSettings userSettings = userSettingRepository.findById(user.getUserId())
                        .orElse(null);

                logger.info("User {} food notice setting: {}", user.getUserId(),
                        userSettings != null ? userSettings.isSettingFoodNotice() : "null");

                if (userSettings != null && userSettings.isSettingFoodNotice()) {
                    LocalDate expirationDate = food.getFoodExpirationDate().toLocalDate();
                    logger.info("Checking food: {}, Expiration date: {}", food.getFoodName(), expirationDate);
                    if (expirationDate.equals(today)) {
                        logger.info("Sending alert for food expiring today: {}", food.getFoodName());
                        sendExpirationAlert(user, food, "오늘");
                    } else if (expirationDate.equals(fiveDaysLater)) {
                        logger.info("Sending alert for food expiring in 5 days: {}", food.getFoodName());
                        sendExpirationAlert(user, food, "5일 후");
                    }
                } else {
                    logger.info("Skipping alert for user {} as food notice is disabled or settings not found", user.getUserId());
                }
            }
        }
        logger.info("checkFoodExpirationAndSendAlerts method ended.");
    }

    private void sendExpirationAlert(User user, Food food, String timeFrame) {
        logger.info("Attempting to send alert for food: {} to user: {}", food.getFoodName(), user.getPhoneNumber());
        Message message = new Message();
        message.setFrom("01052740124"); // 실제 등록된 발신번호
        message.setTo(user.getPhoneNumber());
        message.setText(String.format("[냉장고 관리 알림] %s의 유통기한이 %s 만료됩니다. 확인해주세요!", food.getFoodName(), timeFrame));

        try {
            logger.info("Sending SMS message: {}", message.getText());
            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
            logger.info("SMS sent result: {} - {}", response.getStatusCode(), response.getStatusMessage());
            if (!response.getStatusCode().equals("2000")) {
                logger.error("Failed to send SMS: {}", response.getStatusMessage());
            }
        } catch (Exception e) {
            logger.error("Exception in sendExpirationAlert: {}", e.getMessage(), e);
        }
    }

}