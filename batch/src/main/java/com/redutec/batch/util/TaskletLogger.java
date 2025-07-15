package com.redutec.batch.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskletLogger {
    /**
     * 배치 Tasklet 실행 후 생성, 삭제, 업데이트된 레코드 수를 로그로 출력합니다.
     *
     * @param action      "Created", "Deleted", "Updated" 중 하나
     * @param recordType  레코드 유형 (예: "MeteredBillingRecord")
     * @param count       처리된 레코드 수
     */
    public static void logRecords(String action, String recordType, long count) {
        if (count > 0) {
            log.info("{} {} records: {}", action, recordType, count);
        }
    }
}