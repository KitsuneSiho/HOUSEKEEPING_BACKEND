package com.housekeeping.service.implement;

import com.housekeeping.entity.UserEntity;
import com.housekeeping.repository.UserRepository;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }
}
