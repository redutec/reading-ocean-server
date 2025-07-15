package com.redutec.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * MeteredBillingRecordJobScheduler는 MeteredBillingRecordJob을 스케줄링하여 주기적으로 실행하는 클래스입니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MeteredBillingRecordJobScheduler {
    private final JobLauncher jobLauncher;
    private final Job meteredBillingRecordJob;

    /**
     * 매일 23시 55분 00초에 MeteredBillingRecordJob을 실행합니다.
     * - JobParameters는 실행 시각을 포함하여 전달됩니다.
     */
    @Scheduled(cron = "0 55 23 * * *", zone = "Asia/Seoul")
    public void runJob() throws Exception {
        log.info("**** [시작] 교육기관에 소속한 활성화된 학생수만큼 사용료를 계산 후 MeteredBillingRecord 테이블에 INSERT");
        // JobParametersBuilder에 현재 시간을 추가하여 배치 작업 실행
        jobLauncher.run(meteredBillingRecordJob, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
        log.info("**** [완료] 교육기관에 소속한 활성화된 학생수만큼 사용료를 계산 후 MeteredBillingRecord 테이블에 INSERT");
    }
}