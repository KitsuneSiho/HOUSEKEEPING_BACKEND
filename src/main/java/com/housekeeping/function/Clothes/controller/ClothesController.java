package com.housekeeping.function.Clothes.controller;

import com.housekeeping.entity.Cloth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public class ClothesController {

    @RestController
    @RequestMapping("/ware")
    public class ClothesController {

       //옷장 아이템 조회
        @GetMapping("/items")
        public List<Cloth> getClothes(@RequestParam String name,
                                      @RequestParam String category,
                                      @RequestParam String details)
    }

}
