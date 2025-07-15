package com.redutec.batch.tasklet;

import com.redutec.batch.util.TaskletLogger;
import com.redutec.core.criteria.InstituteCriteria;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.MeteredBillingRecord;
import com.redutec.core.meta.InstituteStatus;
import com.redutec.core.meta.StudentStatus;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.MeteredBillingRecordRepository;
import com.redutec.core.repository.StudentRepository;
import com.redutec.core.specification.InstituteSpecification;
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
import java.util.ArrayList;
import java.util.List;

/**
 * MeteredBillingRecordTasklet은 교육기관에 소속한 활성화된 학생수만큼 사용료를 계산 후
 * MeteredBillingRecord 테이블에 INSERT하는 작업을 처리하는 Tasklet입니다.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MeteredBillingRecordTasklet implements Tasklet {
    private final MeteredBillingRecordRepository meteredBillingRecordRepository;
    private final InstituteRepository instituteRepository;
    private final StudentRepository studentRepository;

    private static final int UNIT_PRICE = 500;

    /**
     * 교육기관에 소속한 활성화된 학생수만큼 사용료를 계산 후 MeteredBillingRecord 테이블에 INSERT하는 메서드입니다.
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
        // 모든 활성화된 교육기관들을 조회
        InstituteCriteria instituteCriteria = new InstituteCriteria(
                null,
                null,
                null,
                null,
                null,
                List.of(InstituteStatus.ACTIVE),
                null,
                null
        );
        List<Institute> institutes = instituteRepository.findAll(
                InstituteSpecification.findWith(instituteCriteria)
        );
        List<MeteredBillingRecord> meteredBillingRecords = new ArrayList<>();
        // 해당 교육기관에 소속한 활성화된 학생수만큼 일일 사용료를 계산
        for (Institute institute : institutes) {
            int activeStudents = studentRepository.countByInstituteAndStatus(institute, StudentStatus.ACTIVE);
            long dailyAmount = (long) activeStudents * UNIT_PRICE;
            // MeteredBillingRecord 엔티티 생성
            MeteredBillingRecord meteredBillingRecord = MeteredBillingRecord.builder()
                    .institute(institute)
                    .billingDate(LocalDate.now())
                    .unitPrice(UNIT_PRICE)
                    .activeStudents(activeStudents)
                    .dailyAmount(dailyAmount)
                    .build();
            meteredBillingRecords.add(meteredBillingRecord);
        }
        // MeteredBillingRecord 테이블에 INSERT
        List<MeteredBillingRecord> createdMeteredBillingRecords = meteredBillingRecordRepository.saveAll(meteredBillingRecords);
        // INSERT한 레코드 수가 0보다 큰 경우 로그를 출력
        TaskletLogger.logRecords("CREATED", "Create meteredBillingRecords", createdMeteredBillingRecords.size());
        return RepeatStatus.FINISHED;
    }
}