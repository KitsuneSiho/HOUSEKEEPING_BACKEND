package com.housekeeping.controller;

import com.housekeeping.DTO.FriendRequestDTO;
import com.housekeeping.entity.FriendRequest;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.RequestStatus;
import com.housekeeping.service.FriendRequestService;
import com.housekeeping.service.FriendService;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/friendRequest")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final UserService userService;
    private final FriendService friendService;

    // 친구 요청 보내기
    @PostMapping("/send")
    public ResponseEntity<FriendRequestDTO> sendFriendRequest(@RequestBody FriendRequestDTO requestDTO) {
        FriendRequestDTO responseDTO = friendRequestService.sendFriendRequest(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 친구 검색 시 내가 요청 보낸 현황 확인
    @GetMapping("/status")
    public ResponseEntity<Map<Long, RequestStatus>> getRequestStatus(
            @RequestParam("senderId") Long senderId,
            @RequestParam("receiverIds") String receiverIds) {
        List<Long> receiverIdList = Arrays.stream(receiverIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        Map<Long, RequestStatus> statusMap = friendRequestService.getFriendRequestStatus(senderId, receiverIdList);
        return ResponseEntity.ok(statusMap);
    }

    // 내가 받은 요청 확인
    @GetMapping("/received")
    public ResponseEntity<List<FriendRequestDTO>> getReceivedFriendRequests(@RequestParam("userId") Long userId) {
        List<FriendRequestDTO> requests = friendRequestService.getReceivedFriendRequests(userId);
        return ResponseEntity.ok(requests);
    }
    // 친구 요청 수락
    @PostMapping("/accept")
    public ResponseEntity<Void> acceptFriendRequest(@RequestParam("requestId") Long requestId) {
        friendRequestService.acceptFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    // 친구 요청 및 친구 관계 모두 취소
    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelFriendRequestAndFriendship(@RequestParam("senderId") Long senderId, @RequestParam("receiverId") Long receiverId) {
        try {

            // 친구 요청 내역 삭제
            friendRequestService.cancelFriendRequest(senderId, receiverId);
            // 친구 관계 삭제
            friendService.deleteFriendship(senderId, receiverId);

            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).build(); // 에러 처리
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<Void> rejectFriendRequest(@RequestParam("senderId") Long senderId, @RequestParam("receiverId") Long receiverId) {
        try {
            friendRequestService.cancelFriendRequest(senderId, receiverId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).build(); // 서버 에러 처리
        }
    }

}
