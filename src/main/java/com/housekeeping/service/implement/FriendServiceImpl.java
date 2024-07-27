package com.housekeeping.service.implement;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.QFriend;
import com.housekeeping.repository.FriendRepository;
import com.housekeeping.service.FriendService;
import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

    @Override
    public List<UserDTO> getFriends(Long userId) {

        List<Tuple> friendIds1 = friendRepository.findFriendsByUserId1(userId);
        List<Tuple> friendIds2 = friendRepository.findFriendsByUserId2(userId);
        List<UserDTO> friendDTOs = new ArrayList<>();

        for (Tuple friend : friendIds1) {
            friendDTOs.add(UserDTO.builder()
                    .userId(friend.get(QFriend.friend.friendUser1.userId))
                    .nickname(friend.get(QFriend.friend.friendUser1.nickname))
                    .build());
        }

        for (Tuple friend : friendIds2) {
            friendDTOs.add(UserDTO.builder()
                    .userId(friend.get(QFriend.friend.friendUser2.userId))
                    .nickname(friend.get(QFriend.friend.friendUser2.nickname))
                    .build());
        }

        return friendDTOs;
    }

}
