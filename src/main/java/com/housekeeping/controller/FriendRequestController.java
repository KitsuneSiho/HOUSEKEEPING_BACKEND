package com.housekeeping.controller;

import com.housekeeping.DTO.FriendRequestDTO;
import com.housekeeping.entity.FriendRequest;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.RequestStatus;
import com.housekeeping.service.FriendRequestService;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
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

    // 친구 요청 보내기
    @PostMapping("/send")
    public ResponseEntity<FriendRequestDTO> sendFriendRequest(@RequestBody FriendRequestDTO requestDTO) {
        FriendRequestDTO responseDTO = friendRequestService.sendFriendRequest(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 친구 검색 시 내가 요청 보낸 현황 확인
    @GetMapping("/status")
    public ResponseEntity<Map<Long, RequestStatus>> getRequestStatus(
            @RequestParam Long senderId,
            @RequestParam String receiverIds) {
        List<Long> receiverIdList = Arrays.stream(receiverIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        Map<Long, RequestStatus> statusMap = friendRequestService.getFriendRequestStatus(senderId, receiverIdList);
        return ResponseEntity.ok(statusMap);
    }

    // 내가 받은 요청 확인
    @GetMapping("/received")
    public ResponseEntity<List<FriendRequestDTO>> getReceivedFriendRequests(@RequestParam Long userId) {
        List<FriendRequestDTO> requests = friendRequestService.getReceivedFriendRequests(userId);
        return ResponseEntity.ok(requests);
    }
    // 친구 요청 수락
    @PostMapping("/accept")
    public ResponseEntity<Void> acceptFriendRequest(@RequestParam Long requestId) {
        friendRequestService.acceptFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

}
