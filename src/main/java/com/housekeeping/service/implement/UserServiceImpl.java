package com.housekeeping.service.implement;

import com.housekeeping.entity.QUser;
import com.housekeeping.entity.User;
import com.housekeeping.repository.UserRepository;
import com.housekeeping.service.UserService;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }
}
