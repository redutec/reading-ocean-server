package com.redutec.batch.tasklet;

import com.redutec.batch.util.TaskletLogger;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.MeteredBilling;
import com.redutec.core.entity.MeteredBillingRecord;
import com.redutec.core.meta.BillingStatus;
import com.redutec.core.repository.MeteredBillingRecordRepository;
import com.redutec.core.repository.MeteredBillingRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MeteredBillingTasklet은 이전 달 교육기관의 월별 사용료를 계산하여
 * MeteredBilling 테이블에 INSERT하는 작업을 처리하는 Tasklet입니다.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MeteredBillingTasklet implements Tasklet {
    private final MeteredBillingRepository meteredBillingRepository;
    private final MeteredBillingRecordRepository meteredBillingRecordRepository;

    /**
     * 이전 달 교육기관의 월별 사용료를 계산하여 MeteredBilling 테이블에 INSERT하는 메서드입니다.
     * 이 메서드는 Spring Batch의 Tasklet에서 호출되어 배치 작업을 처리합니다.
     *
     * @param contribution Step의 기여도 정보
     * @param chunkContext Chunk 관련 컨텍스트 정보
     * @return 작업 상태를 나타내는 RepeatStatus (FINISHED 반환 시 작업 완료)
     */
    @Override
    @Transactional
    public RepeatStatus execute(
            @NonNull StepContribution contribution,
            @NonNull ChunkContext chunkContext
    ) {
        // 배치가 실행된 시점의 이전 달 교육기관의 MeteredBillingRecord 엔티티들이 있는지 조회
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        LocalDate billingPeriodStartDate = previousMonth.atDay(1);
        LocalDate billingPeriodEndDate = previousMonth.atEndOfMonth();
        List<MeteredBillingRecord> meteredBillingRecords = meteredBillingRecordRepository.findByUsageDateBetween(
                billingPeriodStartDate,
                billingPeriodEndDate
        );
        // MeteredBillingRecord 엔티티들을 각 교육기관별로 구분하여 청구 금액을 계산 후 MeteredBilling 엔티티를 생성하여 INSERT
        Map<Institute, List<MeteredBillingRecord>> recordsByInstitute = meteredBillingRecords.stream()
                .collect(Collectors.groupingBy(MeteredBillingRecord::getInstitute));
        List<MeteredBilling> monthlyMeteredBillings = new ArrayList<>();
        for (Map.Entry<Institute, List<MeteredBillingRecord>> entry : recordsByInstitute.entrySet()) {
            Institute institute = entry.getKey();
            long totalInvoiceAmount = entry.getValue().stream()
                    .mapToLong(MeteredBillingRecord::getDailyAmount)
                    .sum();
            MeteredBilling newBilling = MeteredBilling.builder()
                    .institute(institute)
                    .billingPeriodStart(billingPeriodStartDate)
                    .billingPeriodEnd(billingPeriodEndDate)
                    .invoiceAmount(totalInvoiceAmount)
                    .billingStatus(BillingStatus.PENDING)
                    .paymentDueDate(billingPeriodEndDate.plusDays(10))
                    .build();
            monthlyMeteredBillings.add(newBilling);
        }
        // MeteredBilling 테이블에 INSERT
        List<MeteredBilling> createdMeteredBillings = meteredBillingRepository.saveAll(monthlyMeteredBillings);
        // INSERT한 이력을 출력
        TaskletLogger.logRecords("Created", "Create metered billings", createdMeteredBillings.size());
        return RepeatStatus.FINISHED;
    }
}