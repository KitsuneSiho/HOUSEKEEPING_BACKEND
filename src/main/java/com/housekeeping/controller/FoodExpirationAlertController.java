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
import org.springframework.web.bind.annotation.RestController;

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

    @Scheduled(cron = "0 46 13 * * ?") // 매일 오후 9시 30분에 실행
    public void checkFoodExpirationAndSendAlerts() {
        System.out.println("checkFoodExpirationAndSendAlerts method started.");
        LocalDate today = LocalDate.now();
        LocalDate fiveDaysLater = today.plusDays(5);

        List<Food> expiringFoods = foodService.findFoodsExpiringBetween(today, fiveDaysLater);
        System.out.println("Expiring foods: " + expiringFoods);

        for (Food food : expiringFoods) {
            User user = food.getUser();
            if (user != null && user.getPhoneNumber() != null) {
                LocalDate expirationDate = food.getFoodExpirationDate().toLocalDate();
                if (expirationDate.equals(today)) {
                    sendExpirationAlert(user, food, "오늘");
                } else if (expirationDate.equals(fiveDaysLater)) {
                    sendExpirationAlert(user, food, "5일 후");
                }
            }
        }
        System.out.println("checkFoodExpirationAndSendAlerts method ended.");
    }

    private void sendExpirationAlert(User user, Food food, String timeFrame) {
        Message message = new Message();
        message.setFrom("01041017756"); // 실제 등록된 발신번호로 변경하세요
        message.setTo(user.getPhoneNumber());
        message.setText(String.format("[냉장고 관리 알림] %s의 유통기한이 %s 만료됩니다. 확인해주세요!", food.getFoodName(), timeFrame));

        try {
            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
            // 응답 상태 코드와 메시지 출력
            System.out.println("SMS sent to user " + user.getUserId() + ": " + response.getStatusCode());
            System.out.println("Response message: " + response.getStatusMessage());

            if (!response.getStatusCode().equals("2000")) {
                System.err.println("Failed to send SMS: " + response.getStatusMessage());
            }
        } catch (Exception e) {
            System.err.println("Exception occurred while sending SMS: " + e.getMessage());
            e.printStackTrace();
        }
    }
}





    /*
    @GetMapping("/test-sms/{userId}")
    public ResponseEntity<String> testSmsService(@PathVariable Long userId, HttpServletRequest request) {
        try {
            User user = userService.getUserById(userId);
            if (user == null || user.getPhoneNumber() == null) {
                return ResponseEntity.badRequest().body("User not found or phone number is not available");
            }

            Message message = new Message();
            message.setFrom("01041017756");
            message.setTo(user.getPhoneNumber());
            message.setText("이것은 테스트 메시지입니다. 유저 ID: " + userId);

            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

            if (response.getStatusCode().equals("2000")) {
                return ResponseEntity.ok("SMS sent successfully to user " + userId + ". Message ID: " + response.getMessageId());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to send SMS. Error: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // 예외의 전체 스택 트레이스를 출력
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            // 요청 정보 추가
            String requestInfo = "Request URL: " + request.getRequestURL() +
                    ", Method: " + request.getMethod() +
                    ", Client IP: " + request.getRemoteAddr();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage() + "\n" +
                            "Stack Trace: " + stackTrace + "\n" +
                            "Request Info: " + requestInfo);
        }
    } */