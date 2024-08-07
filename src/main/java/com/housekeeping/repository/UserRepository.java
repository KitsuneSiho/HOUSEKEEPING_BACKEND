package com.housekeeping.repository;

import com.housekeeping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname);
    List<User> findByNicknameContainingIgnoreCase(String nickname);
    User findByUsername(String username);
    boolean existsByNickname(String nickname);
    Optional<User> findByEmail(String email);
}