package com.housekeeping.repository;

import com.housekeeping.entity.FriendRequest;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findByRequestSenderAndRequestReceiver(User requestSender, User requestReceiver);
    List<FriendRequest> findByRequestSender_UserIdAndRequestReceiver_UserIdIn(Long senderId, List<Long> receiverIds);
    List<FriendRequest> findByRequestReceiver_UserIdAndRequestStatus(Long userId, RequestStatus PENDING);

}
