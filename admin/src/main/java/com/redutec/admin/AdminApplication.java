package com.redutec.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.redutec.admin", "com.redutec.core"})
@EnableJpaRepositories(basePackages = "com.redutec.core.repository") // 리포지토리 패키지 스캔
@EntityScan(basePackages = "com.redutec.core.entity") // 엔티티 패키지 스캔
@EnableJpaAuditing
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}