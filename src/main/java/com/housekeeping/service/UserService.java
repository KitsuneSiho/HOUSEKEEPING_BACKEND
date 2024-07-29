package com.housekeeping.service;

import com.housekeeping.entity.user.UserEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    UserEntity getUserById(Long id);
    UserEntity saveUser(UserEntity user);
    UserEntity getUserByNickname(String nickname);
}
