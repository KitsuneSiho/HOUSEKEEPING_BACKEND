package com.housekeeping.controller;

import com.housekeeping.service.ReissueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * refresh 토큰으로 재발급 요청 처리
 * refresh rotate 적용
 */
@RestController
@RequiredArgsConstructor
public class ReissueController {
    private final ReissueService reissueService;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return reissueService.reissueTokens(request, response);
    }

    @PostMapping("/reissue/socket")
    public ResponseEntity<?> reissueSocket(@RequestParam("nickname") String nickname) {
        return reissueService.reissueSocketTokens(nickname);
    }
}