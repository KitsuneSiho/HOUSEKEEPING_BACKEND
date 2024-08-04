package com.housekeeping.service;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserService {

    // 회원 번호를 기준으로 유저 정보를 반환
    User getUserById(Long id);
    // 유저 정보를 저장/업데이트
    User saveUser(User user);
    // 특정 닉네임을 가진 유저의 정보를 반환
    User getUserByNickname(String nickname);

}