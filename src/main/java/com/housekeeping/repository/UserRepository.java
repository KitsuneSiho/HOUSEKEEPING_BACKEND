package com.housekeeping.repository;


import com.housekeeping.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // 닉네임으로 유저 정보를 반환
    User findByNickname(String nickname);
}
