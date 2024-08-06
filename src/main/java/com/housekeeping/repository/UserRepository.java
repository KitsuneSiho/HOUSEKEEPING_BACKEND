package com.housekeeping.repository;

import com.housekeeping.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 닉네임으로 유저 정보를 반환
    UserEntity findByNickname(String nickname);
    List<UserEntity> findByNicknameContainingIgnoreCase(String nickname);
    UserEntity findByUsername(String username);

}
