package com.housekeeping.service;

import com.housekeeping.entity.RefreshEntity;
import com.housekeeping.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshRepository refreshRepository;

    @Transactional
    public void saveRefresh(String nickname, Integer expireS, String refresh) {
        // 기존 리프레시 토큰 삭제
        refreshRepository.deleteByNickname(nickname);

        // 새 리프레시 토큰 저장
        RefreshEntity refreshEntity = RefreshEntity.builder()
                .nickname(nickname)
                .refresh(refresh)
                .expiration(LocalDateTime.now().plusSeconds(expireS))
                .build();
        refreshRepository.save(refreshEntity);
    }
}