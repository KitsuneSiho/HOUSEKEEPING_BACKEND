package com.housekeeping.service.implement;

import com.housekeeping.DTO.FriendRequestDTO;
import com.housekeeping.entity.FriendRequest;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.RequestStatus;
import com.housekeeping.repository.FriendRequestRepository;
import com.housekeeping.service.FriendRequestService;
import com.housekeeping.service.FriendService;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService; // Assuming you have a UserService to fetch users
    private final FriendService friendService;

    @Override
    public FriendRequestDTO sendFriendRequest(FriendRequestDTO requestDTO) {
        User sender = userService.getUserById(requestDTO.getRequestSenderId());
        User receiver = userService.getUserById(requestDTO.getRequestReceiverId());

        Optional<FriendRequest> existingRequest = friendRequestRepository.findByRequestSenderAndRequestReceiver(sender, receiver);

        if (existingRequest.isPresent()) {
            // Convert existing FriendRequest to DTO and return
            return convertToDTO(existingRequest.get());
        }

        FriendRequest friendRequest = FriendRequest.builder()
                .requestSender(sender)
                .requestReceiver(receiver)
                .requestStatus(RequestStatus.PENDING)
                .requestDate(LocalDateTime.now())
                .build();

        FriendRequest savedRequest = friendRequestRepository.save(friendRequest);
        return convertToDTO(savedRequest);
    }

    public Map<Long, RequestStatus> getFriendRequestStatus(Long senderId, List<Long> receiverIds) {
        // 요청 상태를 조회합니다.
        List<FriendRequest> requests = friendRequestRepository.findByRequestSender_UserIdAndRequestReceiver_UserIdIn(senderId, receiverIds);

        // 상태 맵을 초기화합니다.
        Map<Long, RequestStatus> statusMap = new HashMap<>();

        // 요청 상태를 상태 맵에 추가합니다.
        for (FriendRequest request : requests) {
            statusMap.put(request.getRequestReceiver().getUserId(), request.getRequestStatus());
        }

        // 요청이 없는 경우에도 처리
        for (Long receiverId : receiverIds) {
            statusMap.putIfAbsent(receiverId, null); // 요청 상태가 없을 때는 null로 설정
        }

        return statusMap;
    }

    public void acceptFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));
        request.setRequestStatus(RequestStatus.ACCEPTED);
        friendRequestRepository.save(request);
        // 친구 관계 생성
        friendService.createFriendRelationship(request.getRequestSender().getUserId(), request.getRequestReceiver().getUserId());
    }

    public List<FriendRequestDTO> getReceivedFriendRequests(Long userId) {
        List<FriendRequest> requests = friendRequestRepository.findByRequestReceiver_UserIdAndRequestStatus(userId, RequestStatus.PENDING);
        return requests.stream()
                .map(request -> FriendRequestDTO.builder()
                        .requestId(request.getRequestId())
                        .requestSenderId(request.getRequestSender().getUserId())
                        .senderNickname(request.getRequestSender().getNickname())
                        .requestReceiverId(request.getRequestReceiver().getUserId())
                        .requestStatus(request.getRequestStatus())
                        .requestDate(request.getRequestDate())
                        .build())
                .collect(Collectors.toList());
    }

    private FriendRequestDTO convertToDTO(FriendRequest friendRequest) {
        return FriendRequestDTO.builder()
                .requestId(friendRequest.getRequestId())
                .requestSenderId(friendRequest.getRequestSender().getUserId())
                .requestReceiverId(friendRequest.getRequestReceiver().getUserId())
                .requestStatus(friendRequest.getRequestStatus())
                .requestDate(friendRequest.getRequestDate())
                .build();
    }

    private FriendRequest convertToEntity(FriendRequestDTO dto) {
        // Method to convert DTO to entity if needed
        User sender = userService.getUserById(dto.getRequestSenderId());
        User receiver = userService.getUserById(dto.getRequestReceiverId());

        return FriendRequest.builder()
                .requestId(dto.getRequestId())
                .requestSender(sender)
                .requestReceiver(receiver)
                .requestStatus(dto.getRequestStatus())
                .requestDate(dto.getRequestDate())
                .build();
    }
}