package com.housekeeping.controller;

import com.housekeeping.entity.Food;
import com.housekeeping.service.FoodService;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class FoodExpirationAlertController {


    private final FoodService foodService;
    private final DefaultMessageService messageService;

    @Autowired
    public FoodExpirationAlertController(FoodService foodService,
                                         @Value("${nurigo.api.key}") String apiKey,
                                         @Value("${nurigo.api.secret}") String apiSecret) {
        this.foodService = foodService;
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    @Scheduled(cron = "0 0 9 * * ?") // 매일 오전 9시에 실행
    public void checkFoodExpirationAndSendAlerts() {
        LocalDate today = LocalDate.now();
        LocalDate fiveDaysLater = today.plusDays(5);

        // 수정: FoodService에 새로운 메서드 추가
        List<Food> expiringFoods = foodService.findFoodsExpiringBetween(today, fiveDaysLater);

        for (Food food : expiringFoods) {
            if (food.getFoodExpirationDate() != null) {
                if (food.getFoodExpirationDate().equals(today)) {
                    sendExpirationAlert(food, "오늘");
                } else if (food.getFoodExpirationDate().equals(fiveDaysLater)) {
                    sendExpirationAlert(food, "5일 후");
                }
            }
        }
    }

    private void sendExpirationAlert(Food food, String timeFrame) {
        String phoneNumber = food.getUser().getPhoneNumber();
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            System.out.println("사용자 " + food.getUser().getUserId() + "의 전화번호가 없습니다.");
            return;
        }

        Message message = new Message();
        message.setFrom("01041017756"); //
        message.setTo(phoneNumber); // 사용자의 전화번호 사용
        message.setText(String.format("[유통기한 알림] %s의 유통기한이 %s 만료됩니다. 확인해주세요!", food.getFoodName(), timeFrame));

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);
    }

    @GetMapping("/test-sms")
    public ResponseEntity<String> testSmsService() {
        Message message = new Message();
        message.setFrom("01041017756"); // 등록된 발신번호 입력
        message.setTo("01041017756"); // 테스트용 수신번호 입력
        message.setText("엿이나 먹으렴.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        if (response.getStatusCode().equals("2000")) {
            return ResponseEntity.ok("SMS sent successfully. Message ID: " + response.getMessageId());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send SMS. Error: " + response.getStatusCode());
        }
    }
}