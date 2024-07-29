package com.housekeeping.service.user;


import com.housekeeping.DTO.oauth2.JoinDto;
import com.housekeeping.entity.user.UserEntity;
import com.housekeeping.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(JoinDto joinDto) {
        // join logic...
        Boolean isExist = userRepository.existsByUsername(joinDto.getUsername());

        if (isExist) {
            System.out.println("already exist user");
            return;
        }

        UserEntity userEntity = UserEntity
                .builder()
                .username(joinDto.getUsername())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(userEntity);
    }
}
