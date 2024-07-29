package com.housekeeping.repository.user;

import com.housekeeping.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
    UserEntity findByNickname(String nickname);
}
