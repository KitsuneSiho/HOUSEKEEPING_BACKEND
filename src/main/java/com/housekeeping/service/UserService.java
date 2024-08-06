package com.housekeeping.service;

import com.housekeeping.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    // 회원 번호를 기준으로 유저 정보를 반환
    UserEntity getUserById(Long id);
    // 유저 정보를 저장/업데이트
    UserEntity saveUser(UserEntity user);
    // 특정 닉네임을 가진 유저의 정보를 반환
    UserEntity getUserByNickname(String nickname);

}
