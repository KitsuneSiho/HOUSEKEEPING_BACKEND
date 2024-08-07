package com.housekeeping.service;

import com.housekeeping.entity.RefreshEntity;
import com.housekeeping.repository.RefreshRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefreshTokenService {
    private final RefreshRepository refreshRepository;

    public RefreshTokenService(RefreshRepository refreshRepository) {
        this.refreshRepository = refreshRepository;
    }

    @Transactional
    public void saveRefresh(String nickname, Integer expireS, String refresh) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be null or empty");
        }

        Optional<RefreshEntity> existingToken = refreshRepository.findByNickname(nickname);

        if (existingToken.isPresent()) {
            RefreshEntity tokenToUpdate = existingToken.get();
            tokenToUpdate.setRefresh(refresh);
            tokenToUpdate.setExpiration(LocalDateTime.now().plusSeconds(expireS));
            refreshRepository.save(tokenToUpdate);
        } else {
            RefreshEntity newToken = RefreshEntity.builder()
                    .nickname(nickname)
                    .refresh(refresh)
                    .expiration(LocalDateTime.now().plusSeconds(expireS))
                    .build();
            refreshRepository.save(newToken);
        }
    }
}