package com.housekeeping.repository;

import com.housekeeping.entity.Attendance;
import com.housekeeping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsByUserAndCheckDate(User user, LocalDate checkDate);
}