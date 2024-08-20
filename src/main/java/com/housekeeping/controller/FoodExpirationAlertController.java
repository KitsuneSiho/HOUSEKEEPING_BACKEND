package com.housekeeping.controller;

import com.housekeeping.entity.Food;
import com.housekeeping.entity.User;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@RestController
public class FoodExpirationAlertController {

    private final FoodService foodService;
    private final UserService userService;
    private final DefaultMessageService messageService;

    @Autowired
    public FoodExpirationAlertController(FoodService foodService,
                                         UserService userService,
                                         @Value("${nurigo.api.key}") String apiKey,
                                         @Value("${nurigo.api.secret}") String apiSecret) {
        this.foodService = foodService;
        this.userService = userService;
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    @Scheduled(cron = "0 0 09 * * ?")
    public void checkFoodExpirationAndSendAlerts() {
        System.out.println("checkFoodExpirationAndSendAlerts method started.");
        LocalDate today = LocalDate.now();
        LocalDate fiveDaysLater = today.plusDays(5);

        System.out.println("Today: " + today + ", Five days later: " + fiveDaysLater);

        List<Food> expiringFoods = foodService.findFoodsExpiringBetween(today, fiveDaysLater);
        System.out.println("Expiring foods: " + expiringFoods);

        for (Food food : expiringFoods) {
            User user = food.getUser();
            if (user != null && user.getPhoneNumber() != null) {
                LocalDate expirationDate = food.getFoodExpirationDate().toLocalDate();
                System.out.println("Checking food: " + food.getFoodName() + ", Expiration date: " + expirationDate);
                if (expirationDate.equals(today)) {
                    System.out.println("Sending alert for food expiring today: " + food.getFoodName());
                    sendExpirationAlert(user, food, "오늘");
                } else if (expirationDate.equals(fiveDaysLater)) {
                    System.out.println("Sending alert for food expiring in 5 days: " + food.getFoodName());
                    sendExpirationAlert(user, food, "5일 후");
                }
            }
        }
        System.out.println("checkFoodExpirationAndSendAlerts method ended.");
    }

    private void sendExpirationAlert(User user, Food food, String timeFrame) {
        System.out.println("Attempting to send alert for food: " + food.getFoodName() + " to user: " + user.getPhoneNumber());
        Message message = new Message();
        message.setFrom("01041017756"); // 실제 등록된 발신번호
        message.setTo(user.getPhoneNumber());
        message.setText(String.format("[냉장고 관리 알림] %s의 유통기한이 %s 만료됩니다. 확인해주세요!", food.getFoodName(), timeFrame));

        try {
            System.out.println("Sending SMS message: " + message.getText());
            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
            System.out.println("SMS sent result: " + response.getStatusCode() + " - " + response.getStatusMessage());
            if (!response.getStatusCode().equals("2000")) {
                System.out.println("Failed to send SMS: " + response.getStatusMessage());
            }
        } catch (Exception e) {
            System.out.println("Exception in sendExpirationAlert: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @GetMapping("/test-food-expiration-alert")
    public ResponseEntity<String> testFoodExpirationAlert() {
        System.out.println("Manual test of food expiration alert started.");
        checkFoodExpirationAndSendAlerts();
        System.out.println("Manual test of food expiration alert completed.");
        return ResponseEntity.ok("Food expiration check completed. Check console for details.");
    }
}