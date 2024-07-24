package com.housekeeping.service;

import com.housekeeping.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
    User getUserById(Long id);
}
