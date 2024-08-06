package com.housekeeping.service;

import com.housekeeping.entity.RefreshEntity;
import com.housekeeping.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);
    private final RefreshRepository refreshRepository;

    @Transactional
    public void saveRefresh(String nickname, Integer expireS, String refresh) {
        logger.debug("Saving refresh token for nickname: {}", nickname);

        if (nickname == null || nickname.isEmpty()) {
            logger.error("Nickname is null or empty");
            throw new IllegalArgumentException("Nickname cannot be null or empty");
        }

        // 기존 리프레시 토큰 삭제
        refreshRepository.deleteByNickname(nickname);

        // 새 리프레시 토큰 저장
        RefreshEntity refreshEntity = RefreshEntity.builder()
                .nickname(nickname)
                .refresh(refresh)
                .expiration(LocalDateTime.now().plusSeconds(expireS))
                .build();
        refreshRepository.save(refreshEntity);
        logger.debug("Refresh token saved successfully for nickname: {}", nickname);
    }
}