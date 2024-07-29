package com.housekeeping.repository.user;

import com.housekeeping.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAndProvider(String email, String provider);
    boolean existsByNickname(String nickname);
    UserEntity findByNickname(String nickname);
}