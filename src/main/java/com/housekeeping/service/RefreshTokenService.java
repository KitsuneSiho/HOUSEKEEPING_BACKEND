package com.housekeeping.service;

import com.housekeeping.entity.RefreshEntity;
import com.housekeeping.repository.RefreshRepository;
import com.housekeeping.jwt.JWTUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefreshTokenService {
    private final RefreshRepository refreshRepository;
    private final JWTUtil jwtUtil;

    public RefreshTokenService(RefreshRepository refreshRepository, JWTUtil jwtUtil) {
        this.refreshRepository = refreshRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public void saveRefreshToken(Long userId, String refreshToken) {
        Optional<RefreshEntity> existingToken = refreshRepository.findByUserId(userId);

        if (existingToken.isPresent()) {
            RefreshEntity tokenToUpdate = existingToken.get();
            tokenToUpdate.setRefresh(refreshToken);
            tokenToUpdate.setExpiration(LocalDateTime.now().plusDays(14));
            refreshRepository.save(tokenToUpdate);
        } else {
            RefreshEntity newToken = RefreshEntity.builder()
                    .userId(userId)
                    .refresh(refreshToken)
                    .expiration(LocalDateTime.now().plusDays(14))
                    .build();
            refreshRepository.save(newToken);
        }
    }

    @Transactional(readOnly = true)
    public boolean validateRefreshToken(Long userId, String refreshToken) {
        Optional<RefreshEntity> refreshEntityOpt = refreshRepository.findByUserId(userId);
        return refreshEntityOpt.map(refreshEntity ->
                refreshEntity.getRefresh().equals(refreshToken) &&
                        refreshEntity.getExpiration().isAfter(LocalDateTime.now()) &&
                        jwtUtil.isValid(refreshToken) &&
                        jwtUtil.getCategory(refreshToken).equals("refresh")
        ).orElse(false);
    }

    @Transactional
    public void deleteRefreshToken(Long userId) {
        refreshRepository.deleteByUserId(userId);
    }

    @Transactional
    public String getRefreshToken(Long userId) {
        return refreshRepository.findByUserId(userId).orElseThrow().getRefresh();
    }
}