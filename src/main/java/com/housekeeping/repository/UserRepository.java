package com.housekeeping.repository;

import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.UserPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    List<User> findByNicknameContainingIgnoreCase(String nickname);
    Optional<User> findByUsername(String username);
    boolean existsByNickname(String nickname);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndUserPlatform(String email, UserPlatform userPlatform);  // Add this line
}