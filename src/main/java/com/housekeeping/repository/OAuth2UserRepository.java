package com.housekeeping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2UserRepository extends JpaRepository<OAuth2UserEntity, Long> {
    OAuth2UserEntity findByUsername(String username);
}
