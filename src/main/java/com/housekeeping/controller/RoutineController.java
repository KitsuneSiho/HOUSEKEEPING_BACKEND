package com.housekeeping.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/routine")
public class RoutineController {

    // 루틴 목록 중 적용할 루틴 선택
    @PostMapping("/apply")
    public void routineApply() {

    }

    // 추천 루틴 조회
    @GetMapping("/recommend/view")
    public void routineRecommendView() {


    }

    // 추천 루틴 수정해서 내 루틴으로 저장
    @PutMapping("/recommend/update")
    public void routineRecommendUpdate() {


    }

    // 사용자 루틴 생성
    @PostMapping("/create")
    public void routineCreate() {


    }

    // 기존 내 루틴 수정
    @PutMapping("/update")
    public void routineUpdate() {


    }

    // 루틴 삭제
    @DeleteMapping("/delete")
    public void routineDelete() {

    }
}
