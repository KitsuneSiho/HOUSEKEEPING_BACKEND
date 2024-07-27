package com.housekeeping.service;

import com.housekeeping.DTO.UserDTO;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface FriendService {

    List<UserDTO> getFriends(Long userId);
}
