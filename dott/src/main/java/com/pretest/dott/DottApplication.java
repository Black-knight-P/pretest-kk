package com.pretest.dott;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DottApplication {

    public static void main(String[] args) {
        SpringApplication.run(DottApplication.class, args);
    }

}
