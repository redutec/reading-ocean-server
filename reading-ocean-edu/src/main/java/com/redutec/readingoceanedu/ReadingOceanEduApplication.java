package com.redutec.readingoceanedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.redutec.reading-ocean-edu", "com.redutec.core"})
@EnableJpaRepositories(basePackages = "com.redutec.core.repository")
@EntityScan(basePackages = "com.redutec.core.entity")
@EnableJpaAuditing
public class ReadingOceanEduApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReadingOceanEduApplication.class, args);
    }
}