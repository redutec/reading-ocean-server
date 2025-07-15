package com.redutec.batch.config;

import com.redutec.batch.tasklet.MeteredBillingRecordTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * MeteredBillingRecordJobConfig는 매일 교육기관에 소속된 활성화 상태의 학생 수만큼 이용료를 계산하여
 * MeteredBillingRecord 테이블에 INSERT 하는 작업을 Spring Batch로 구성하는 설정 클래스입니다.
 * - Job과 Step을 정의하여 주기적으로 Tasklet을 실행합니다.
 */
@Configuration
@RequiredArgsConstructor
public class MeteredBillingRecordJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    /**
     * MeteredBillingRecord 테이블에 INSERT하는 작업(Job)을 정의합니다.
     * - 이 Job은 MeteredBillingRecordTasklet을 실행하는 단일 Step으로 구성됩니다.
     *
     * @param meteredBillingRecordStep MeteredBillingRecordTasklet을 실행하는 Step
     * @return Job 객체
     */
    @Bean
    public Job meteredBillingRecordJob(Step meteredBillingRecordStep) {
        return new JobBuilder("meteredBillingRecordJob", jobRepository)
                .start(meteredBillingRecordStep)
                .build();
    }

    /**
     * MeteredBillingRecord 테이블에 INSERT하는 Step을 정의합니다.
     *
     * @param meteredBillingRecordTasklet MeteredBillingRecord 테이블에 INSERT하는 작업을 처리하는 Tasklet
     * @return Step 객체
     */
    @Bean
    public Step meteredBillingRecordStep(MeteredBillingRecordTasklet meteredBillingRecordTasklet) {
        return new StepBuilder("meteredBillingRecordStep", jobRepository)
                .tasklet(meteredBillingRecordTasklet, transactionManager)
                .build();
    }
}