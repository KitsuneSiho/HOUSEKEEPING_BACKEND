package com.housekeeping.service;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.UserPlatform;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    User getUserById(Long id);
    UserDTO getUserDTOById(Long id);
    User saveUser(User user);
    User getUserByNickname(String nickname);
    UserDTO completeRegistration(UserDTO userDTO);
    boolean isNewUser(String email, UserPlatform platform);
    // 일단 임시로 만든 신규 유저 판별
    boolean isNewUserTemp(String nickname);
    UserDTO updateUserInfo(UserDTO userDTO);
    void deleteUser(Long userId);
    void updateUserStatus(Long userId, boolean isOnline);
    void updateUserStatusByNickname(String nickname, boolean isOnline);
    int getUserLevel(Long userId);

    UserDTO updateProfileImage(Long userId, String profileImageUrl);
}
