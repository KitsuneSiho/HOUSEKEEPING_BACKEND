package com.housekeeping.service.user;

import com.housekeeping.entity.user.RefreshEntity;
import com.housekeeping.repository.user.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshRepository refreshRepository;

    @Transactional
    public void saveRefresh(String email, Integer expireS, String refresh) {
        RefreshEntity refreshEntity = RefreshEntity.builder()
                .username(email)
                .refresh(refresh)
                .expiration(new Date(System.currentTimeMillis() + expireS * 1000L).toString())
                .build();

        refreshRepository.save(refreshEntity);
    }
}
