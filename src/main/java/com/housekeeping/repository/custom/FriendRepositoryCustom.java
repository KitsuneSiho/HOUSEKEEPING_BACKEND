package com.housekeeping.repository.custom;

import com.housekeeping.entity.Friend;
import com.querydsl.core.Tuple;

import java.util.List;
import java.util.Optional;

public interface FriendRepositoryCustom {

    // 회원 번호를 기준으로 모든 친구들의 회원 번호와 닉네임 리스트를 출력 (findFriendsByUserId2) 와 같이 써야 함
    List<Tuple> findFriendsByUserId1(Long userId);
    // 회원 번호를 기준으로 모든 친구들의 회원 번호와 닉네임 리스트를 출력 (findFriendsByUserId1) 와 같이 써야 함
    List<Tuple> findFriendsByUserId2(Long userId);
    // 회원 번호를 기준으로 접속중인 친구들의 회원 번호와 닉네임 리스트를 출력 (fineOnlineFriends2) 와 같이 써야 함
    List<Tuple> fineOnlineFriends1(Long userId);
    // 회원 번호를 기준으로 접속중인 친구들의 회원 번호와 닉네임 리스트를 출력 (fineOnlineFriends1) 와 같이 써야 함
    List<Tuple> fineOnlineFriends2(Long userId);
    // user1과 2가 맺은 친구관계 찾기
    Optional<Friend> findByUsers(Long userId1, Long userId2);
}
