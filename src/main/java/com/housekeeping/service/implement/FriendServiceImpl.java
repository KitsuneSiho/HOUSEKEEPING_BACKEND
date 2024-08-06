package com.housekeeping.service.implement;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.Friend;
import com.housekeeping.entity.QFriend;
import com.housekeeping.entity.User;
import com.housekeeping.repository.FriendRepository;
import com.housekeeping.repository.UserRepository;
import com.housekeeping.service.FriendService;
import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

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

    @Override
    public List<UserDTO> getOnlineFriends(Long userId) {

        List<Tuple> friendIds1 = friendRepository.fineOnlineFriends1(userId);
        List<Tuple> friendIds2 = friendRepository.fineOnlineFriends2(userId);
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

    @Override
    public List<UserDTO> searchUsersByNickname(String nickname) {
        List<User> users = userRepository.findByNicknameContainingIgnoreCase(nickname);
        return users.stream()
                .map(user -> UserDTO.builder()
                        .userId(user.getUserId())
                        .nickname(user.getNickname())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void createFriendRelationship(Long userId1, Long userId2) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 친구 관계 생성
        Friend friend = Friend.builder()
                .friendUser1(user1)
                .friendUser2(user2)
                .friendDate(LocalDateTime.now())
                .build();

        friendRepository.save(friend);
    }

    @Override
    public void deleteFriendship(Long userId1, Long userId2) {
        // 친구 관계를 찾기
        Friend friend = friendRepository
                .findByUsers(userId1, userId2)
                .orElseThrow(() -> new RuntimeException("Friend relationship not found"));

        // 친구 관계를 삭제
        friendRepository.delete(friend);
    }


}
