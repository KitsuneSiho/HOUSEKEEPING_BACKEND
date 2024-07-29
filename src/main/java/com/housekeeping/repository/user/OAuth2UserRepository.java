package com.housekeeping.repository.user;


import com.housekeeping.entity.user.OAuth2UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2UserRepository extends JpaRepository<OAuth2UserEntity, Long> {
    OAuth2UserEntity findByUsername(String username);
}
