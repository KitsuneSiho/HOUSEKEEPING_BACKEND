package com.housekeeping.repository;

import com.housekeeping.entity.Friend;
import com.housekeeping.repository.custom.FriendRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long>, FriendRepositoryCustom {
}
