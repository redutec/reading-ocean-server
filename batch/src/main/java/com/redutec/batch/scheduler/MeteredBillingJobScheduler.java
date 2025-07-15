package com.redutec.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * MeteredBillingJobScheduler는 MeteredBillingJob을 스케줄링하여 주기적으로 실행하는 클래스입니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MeteredBillingJobScheduler {
    private final JobLauncher jobLauncher;
    private final Job meteredBillingJob;

    /**
     * 매월 10시 00분 00초에 MeteredBillingJob을 실행합니다.
     * - JobParameters는 실행 시각을 포함하여 전달됩니다.
     */
    @Scheduled(cron = "0 0 10 1 * *", zone = "Asia/Seoul")
    public void runJob() throws Exception {
        log.info("**** [시작] 이전 달 교육기관의 월별 사용료 청구서 데이터를 MeteredBilling 테이블에 INSERT");
        // JobParametersBuilder에 현재 시간을 추가하여 배치 작업 실행
        jobLauncher.run(meteredBillingJob, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
        log.info("**** [완료] 이전 달 교육기관의 월별 사용료 청구서 데이터를 MeteredBilling 테이블에 INSERT");
    }
}