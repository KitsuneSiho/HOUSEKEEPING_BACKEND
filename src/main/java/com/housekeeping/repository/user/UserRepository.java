package com.housekeeping.repository.user;

import com.housekeeping.entity.enums.UserPlatform;
import com.housekeeping.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndUserPlatform(String email, UserPlatform userPlatform);
    boolean existsByNickname(String nickname);
    User findByNickname(String nickname);
    Optional<User> findByEmail(String email);
}
