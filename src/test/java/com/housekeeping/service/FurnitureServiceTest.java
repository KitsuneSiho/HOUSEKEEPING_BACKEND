package com.housekeeping.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class FurnitureServiceTest {

    @Autowired
    private FurnitureService furnitureService;

    @Test
    public void getFurnitureListTest () {

        log.info(furnitureService.getFurnitureList(1));
    }
}
