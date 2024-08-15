package com.housekeeping.repository;

import com.housekeeping.entity.Routine;
import com.housekeeping.repository.custom.RoutineRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long>, RoutineRepositoryCustom {
    // 기본 JPA 메서드 사용
    // 루틴 그룹명으로 루틴을 조회하는 메소드
    List<Routine> findByRoutineGroupName(String routineGroupName);

}
