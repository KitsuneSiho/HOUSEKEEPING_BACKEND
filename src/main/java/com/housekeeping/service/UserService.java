package com.housekeeping.service;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.UserPlatform;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    // 회원 번호를 기준으로 유저 정보를 반환
    User getUserById(Long id);
    UserDTO getUserDTOById(Long id);
    User saveUser(User user);
    // 특정 닉네임을 가진 유저의 정보를 반환
    User getUserByNickname(String nickname);

    UserDTO completeRegistration(UserDTO userDTO);

    boolean isNewUser(String email, UserPlatform platform);

    int getUserLevel(Long userId);
    UserDTO updateUserInfo(UserDTO userDTO);
    void deleteUser(Long userId);
    void updateUserStatus(Long userId, boolean isOnline);
    void updateUserStatusByNickname(String nickname, boolean isOnline);
}
