package com.housekeeping.repository;

import com.housekeeping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // 닉네임으로 유저 정보를 반환
    User findByNickname(String nickname);
    List<User> findByNicknameContainingIgnoreCase(String nickname);
    User findByUsername(String username);
    boolean existsByNickname(String nickname);


}
