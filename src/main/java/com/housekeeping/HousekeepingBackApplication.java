package com.housekeeping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.housekeeping.repository")
public class HousekeepingBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousekeepingBackApplication.class, args);
    }

}
