package com.housekeeping.controller;

import com.housekeeping.entity.User;
import com.housekeeping.service.RoomService;
import com.housekeeping.service.UserService;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
public class PollutionAlertController {

    private static final Logger logger = LoggerFactory.getLogger(PollutionAlertController.class);

    private final RoomService roomService;
    private final UserService userService;
    private final DefaultMessageService messageService;

    @Autowired
    public PollutionAlertController(RoomService roomService,
                                    UserService userService,
                                    @Value("${nurigo.api.key}") String apiKey,
                                    @Value("${nurigo.api.secret}") String apiSecret) {
        this.roomService = roomService;
        this.userService = userService;
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }



    @PostMapping("/pollution/alert")
    public ResponseEntity<String> sendPollutionAlert(@RequestParam Long roomId, @RequestParam double pollution) {
        logger.info("Received request to send pollution alert for roomId: {} with pollution level: {}", roomId, pollution);
        checkPollutionAndSendAlert(roomId);
        return ResponseEntity.ok("Pollution alert check completed.");
    }

    public void checkPollutionAndSendAlert(Long roomId) {
        logger.info("Checking pollution level for roomId: {}", roomId);
        double roomPollution = roomService.getRoomPollution(roomId); // 방의 오염도 가져오기
        logger.info("Room pollution level: {}", roomPollution);

        if (roomPollution > 80) {
            User user = roomService.getRoomOwner(roomId); // 방 주인 가져오기
            if (user != null && user.getPhoneNumber() != null) {
                logger.info("Pollution level exceeds 50. Sending alert to user: {}", user.getPhoneNumber());
                sendPollutionAlert(user, roomPollution);
            } else {
                logger.warn("User or user's phone number is null for roomId: {}", roomId);
            }
        } else {
            logger.info("Pollution level does not exceed 50. No alert sent for roomId: {}", roomId);
        }
    }

    private void sendPollutionAlert(User user, double pollutionLevel) {
        Message message = new Message();
        message.setFrom("01041017756"); // 실제 등록된 발신번호
        message.setTo(user.getPhoneNumber());
        message.setText(String.format("[환경 알림] 방의 오염도가 %s%%를 넘었습니다. 확인해주세요!", pollutionLevel));

        try {
            logger.info("Attempting to send SMS to {}", user.getPhoneNumber());
            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
            logger.info("SMS sent result: {} - {}", response.getStatusCode(), response.getStatusMessage());
            if (!response.getStatusCode().equals("2000")) {
                logger.error("Failed to send SMS: {}", response.getStatusMessage());
            }
        } catch (Exception e) {
            logger.error("Exception occurred while sending SMS: {}", e.getMessage(), e);
        }

    }
    @Scheduled(cron = "0 47 21 * * ?")
    public void checkPollutionLevelsAtNight() {
        logger.info("Scheduled task to check pollution levels at 20:00 started.");
        List<Long> roomIds = roomService.getAllRoomIds(); // 모든 방의 ID를 가져오는 메서드가 필요합니다.

        for (Long roomId : roomIds) {
            checkPollutionAndSendAlert(roomId);
        }
        logger.info("Scheduled task to check pollution levels at 20:00 completed.");
    }
}

