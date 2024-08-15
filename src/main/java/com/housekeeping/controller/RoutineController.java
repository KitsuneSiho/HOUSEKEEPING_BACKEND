package com.housekeeping.controller;

import com.housekeeping.DTO.RoutineDTO;
import com.housekeeping.entity.RecommendRoutine;
import com.housekeeping.entity.Routine;
import com.housekeeping.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/routine")
public class RoutineController {

    @Autowired
    private final RoutineService routineService;

    // 사용자 id에 해당하는 루틴 그룹명 검색
    @GetMapping("/groups")
    public List<String> getRoutineGroupsByUserId(@RequestParam("userId") Long userId) {
        return routineService.getRoutineGroupNamesByUserId(userId);
    }

    // 만들어진 루틴 보기
    @GetMapping("/group/{groupName}")
    public List<RoutineDTO> getRoutinesByGroupName(@PathVariable("groupName") String groupName) {
        return routineService.findByGroupName(groupName);
    }

    // 단일 루틴 추가하기
    @PostMapping("/add")
    public RoutineDTO addRoutine(@RequestBody RoutineDTO routineDTO) {
        // 서비스에서 루틴을 추가하고 DTO로 변환된 결과를 반환
        return routineService.addRoutine(routineDTO);
    }

    // 루틴 그룹 통째로 추가하기
    @PostMapping("/group/add")
    public void addRoutineGroup(@RequestBody List<RoutineDTO> routineDTOs) {
        routineService.addRoutineGroup(routineDTOs);
    }

    // 루틴 수정하기
    @PutMapping("/update")
    public RoutineDTO updateRoutine(@RequestBody RoutineDTO routineDTO) {
        return routineService.updateRoutine(routineDTO);
    }

    // 루틴 삭제하기
    @DeleteMapping("/delete/{routineId}")
    public void deleteRoutine(@PathVariable("routineId") Long routineId) {
        routineService.deleteRoutine(routineId);
    }

    // 루틴 그룹 통째로 삭제하기
    @DeleteMapping("/deleteGroup/{groupName}")
    public ResponseEntity<String> deleteRoutineGroup(@PathVariable("groupName") String groupName) {
        try {
            routineService.deleteRoutineGroup(groupName);
            return ResponseEntity.ok("루틴 그룹이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            // 예외 발생 시 로그 기록 및 에러 메시지 반환
            System.err.println("Error deleting routine group: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("루틴 그룹 삭제 중 오류가 발생했습니다.");
        }
    }

    // 추천 루틴 조회
    @GetMapping("/recommend/view")
    public ResponseEntity<List<RecommendRoutine>> getRecommendRoutines() {
        List<RecommendRoutine> recommendRoutines = routineService.getAllRecommendRoutines();
        return ResponseEntity.ok(recommendRoutines);
    }

    // 루틴 목록 중 적용할 루틴 선택
    @PostMapping("/apply")
    public void routineApply() {

    }



    // 추천 루틴 수정해서 내 루틴으로 저장
    @PutMapping("/recommend/update")
    public void routineRecommendUpdate() {


    }

    // 사용자 루틴 생성
    @PostMapping("/create")
    public void routineCreate() {


    }

}
