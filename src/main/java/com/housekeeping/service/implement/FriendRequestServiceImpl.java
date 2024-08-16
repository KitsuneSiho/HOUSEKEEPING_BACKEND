package com.housekeeping.service.implement;

import com.housekeeping.DTO.FriendRequestDTO;
import com.housekeeping.entity.FriendRequest;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.RequestStatus;
import com.housekeeping.repository.FriendRequestRepository;
import com.housekeeping.repository.custom.FriendRequestRepositoryCustom;
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
    private final FriendRequestRepositoryCustom friendRequestRepositoryCustom;
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
        Map<Long, RequestStatus> statusMap = new HashMap<>();

        for (Long receiverId : receiverIds) {
            Optional<FriendRequest> friendRequestOptional = friendRequestRepositoryCustom.findByUsers(senderId, receiverId);

            if (friendRequestOptional.isPresent()) {
                FriendRequest friendRequest = friendRequestOptional.get();
                statusMap.put(receiverId, friendRequest.getRequestStatus());
            } else {
                statusMap.put(receiverId, null); // 친구 요청이 없을 경우 null로 설정
            }
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

    public void cancelFriendRequest(Long senderId, Long receiverId) {
        // QueryDSL로 작성한 메서드를 사용하여 친구 요청을 찾음
        FriendRequest friendRequest = friendRequestRepositoryCustom
                .findByUsers(senderId, receiverId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        // 요청 삭제
        friendRequestRepository.delete(friendRequest);
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