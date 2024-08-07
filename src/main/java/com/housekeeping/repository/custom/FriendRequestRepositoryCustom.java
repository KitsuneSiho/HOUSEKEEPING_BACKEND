package com.housekeeping.repository.custom;

import com.housekeeping.entity.FriendRequest;
import java.util.Optional;

public interface FriendRequestRepositoryCustom {
    Optional<FriendRequest> findByUsers(Long userId1, Long userId2);
}
