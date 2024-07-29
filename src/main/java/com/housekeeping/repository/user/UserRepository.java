package com.housekeeping.repository.user;

import com.housekeeping.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndProvider(String email, String provider);
    boolean existsByNickname(String nickname);
    User findByNickname(String nickname);
}