package com.housekeeping.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/tip")
public class TipController {

    @GetMapping("/list")
    public String list() {
        return "Hello World";

    }


}
