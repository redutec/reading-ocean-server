package com.redutec.teachingocean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.redutec.teachingocean", "com.redutec.core"})
@EnableJpaRepositories(basePackages = "com.redutec.core.repository")
@EntityScan(basePackages = "com.redutec.core.entity")
@EnableJpaAuditing
public class TeachingOceanApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeachingOceanApplication.class, args);
    }
}