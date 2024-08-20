package com.housekeeping.service.implement;

import com.housekeeping.DTO.UserDTO;
import com.housekeeping.entity.Attendance;
import com.housekeeping.entity.LevelEXPTable;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.UserPlatform;
import com.housekeeping.repository.AttendanceRepository;
import com.housekeeping.repository.LevelEXPTableRepository;
import com.housekeeping.repository.RefreshRepository;
import com.housekeeping.repository.UserRepository;
import com.housekeeping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;
    private final AttendanceRepository attendanceRepository;
    private final LevelEXPTableRepository levelEXPTableRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDTO getUserDTOById(Long id) {
        User user = getUserById(id);
        return convertToDTO(user);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public int getUserLevel(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("level: " + user.getLevel().getLevelLevel());

        return user.getLevel().getLevelLevel();
    }

    @Override
    public UserDTO completeRegistration(UserDTO userDTO) {
        // 구현 로직
        return userDTO;
    }

    @Override
    public boolean isNewUser(String email, UserPlatform platform) {
        // 구현 로직
        return false;
    }

    @Override
    public boolean isNewUserTemp(String nickname) {
        // 구현 로직
        return nickname.startsWith("kakao_") || nickname.startsWith("naver_") || nickname.startsWith("google_");
    }

    @Override
    public UserDTO updateUserInfo(UserDTO userDTO) {
        User user = getUserById(userDTO.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setName(userDTO.getName());
        user.setNickname(userDTO.getNickname());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        User updatedUser = saveUser(user);
        return convertToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        refreshRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public void updateUserStatus(Long userId, boolean isOnline) {
        User user = getUserById(userId);
        user.setUserIsOnline(isOnline);
        saveUser(user);
    }

    @Override
    public void updateUserStatusByNickname(String nickname, boolean isOnline) {
        User user = getUserByNickname(nickname);
        user.setUserIsOnline(isOnline);
        saveUser(user);
    }

    @Override
    public UserDTO updateProfileImage(Long userId, String profileImageUrl) {
        User user = getUserById(userId);
        user.setProfileImageUrl(profileImageUrl);
        User updatedUser = saveUser(user);
        return convertToDTO(updatedUser);
    }

    @Override
    public boolean checkAttendance(Long userId) {
        User user = getUserById(userId);
        LocalDate today = LocalDate.now();

        if (attendanceRepository.existsByUserAndCheckDate(user, today)) {
            return false; // 이미 출석체크를 했음
        }

        // 출석체크 기록
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setCheckDate(today);
        attendanceRepository.save(attendance);

        // 경험치 추가 (예: 10 EXP)
        int expGain = 10;
        user.setUserEXP(user.getUserEXP() + expGain);

        // 레벨업 체크
        checkAndUpdateLevel(user);

        saveUser(user);
        return true;
    }

    private void checkAndUpdateLevel(User user) {
        LevelEXPTable nextLevel = levelEXPTableRepository.findByLevelLevel(user.getLevel().getLevelLevel() + 1)
                .orElse(null);

        if (nextLevel != null && user.getUserEXP() >= nextLevel.getLevelRequireEXP()) {
            user.setLevel(nextLevel);
        }
    }

    @Override
    public boolean isAttendanceCheckedToday(Long userId) {
        User user = getUserById(userId);
        LocalDate today = LocalDate.now();
        return attendanceRepository.existsByUserAndCheckDate(user, today);
    }


    @Override
    public UserDTO convertToDTO(User user) {
        LevelEXPTable currentLevel = user.getLevel();
        LevelEXPTable nextLevel = levelEXPTableRepository.findByLevelLevel(currentLevel.getLevelLevel() + 1).orElse(null);

        int currentLevelExp = currentLevel.getLevelRequireEXP();
        int nextLevelExp = nextLevel != null ? nextLevel.getLevelRequireEXP() : currentLevel.getLevelRequireEXP();
        int expForNextLevel = nextLevelExp - currentLevelExp;
        int userProgressInLevel = user.getUserEXP() - currentLevelExp;

        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .userPlatform(user.getUserPlatform())
                .profileImageUrl(user.getProfileImageUrl())
                .level(user.getLevel().getLevelLevel())
                .levelName(user.getLevel().getLevelName())
                .exp(userProgressInLevel)
                .nextLevelExp(expForNextLevel)
                .build();
    }
}