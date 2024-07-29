package com.housekeeping.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    // 홈 화면에 간단한 텍스트 출력
    @GetMapping("/")
    public String test() {
        return "Hello World";
    }

}
