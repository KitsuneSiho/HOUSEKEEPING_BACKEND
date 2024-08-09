package com.housekeeping.service;

import com.housekeeping.entity.RefreshEntity;
import com.housekeeping.repository.RefreshRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefreshTokenService {
    private final RefreshRepository refreshRepository;

    public RefreshTokenService(RefreshRepository refreshRepository) {
        this.refreshRepository = refreshRepository;
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Transactional
    public void saveRefresh(String nickname, Integer expireS, String refresh) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be null or empty");
        }

        String hashedRefresh = hashToken(refresh);

        Optional<RefreshEntity> existingToken = refreshRepository.findByNickname(nickname);

        if (existingToken.isPresent()) {
            RefreshEntity tokenToUpdate = existingToken.get();
            tokenToUpdate.setRefresh(hashedRefresh);
            tokenToUpdate.setExpiration(LocalDateTime.now().plusSeconds(expireS));
            refreshRepository.save(tokenToUpdate);
        } else {
            RefreshEntity newToken = RefreshEntity.builder()
                    .nickname(nickname)
                    .refresh(hashedRefresh)
                    .expiration(LocalDateTime.now().plusSeconds(expireS))
                    .build();
            refreshRepository.save(newToken);
        }
    }
}