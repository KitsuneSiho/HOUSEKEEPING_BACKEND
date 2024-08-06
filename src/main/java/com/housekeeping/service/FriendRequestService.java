package com.housekeeping.service;

import com.housekeeping.DTO.FriendRequestDTO;
import com.housekeeping.entity.enums.RequestStatus;

import java.util.List;
import java.util.Map;

public interface FriendRequestService {

    // 팔로우 요청 보내기
    FriendRequestDTO sendFriendRequest(FriendRequestDTO requestDTO);
    // 검색된 리스트 중 팔로우 현황
    Map<Long, RequestStatus> getFriendRequestStatus(Long senderId, List<Long> receiverIds);
    // 친구 요청 목록 확인
    List<FriendRequestDTO> getReceivedFriendRequests(Long userId);
    // 친구 요청 수락
    void acceptFriendRequest(Long requestId);
}
