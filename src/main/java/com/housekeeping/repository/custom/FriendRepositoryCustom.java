package com.housekeeping.repository.custom;

import com.querydsl.core.Tuple;

import java.util.List;

public interface FriendRepositoryCustom {

    List<Tuple> findFriendsByUserId1(Long userId);
    List<Tuple> findFriendsByUserId2(Long userId);

}
