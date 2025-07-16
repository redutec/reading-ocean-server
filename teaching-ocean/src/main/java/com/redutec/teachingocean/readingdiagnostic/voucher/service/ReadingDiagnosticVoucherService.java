package com.redutec.teachingocean.readingdiagnostic.voucher.service;

import com.redutec.core.dto.ReadingDiagnosticVoucherDto;

public interface ReadingDiagnosticVoucherService {
    /**
     * 현재 로그인한 교육기관이 보유한 독서능력진단평가 바우처 목록 조회
     * @param findReadingDiagnosticVoucherRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 독서능력진단평가 바우처 목록 및 페이징 정보
     */
    ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherPageResponse find(ReadingDiagnosticVoucherDto.FindReadingDiagnosticVoucherRequest findReadingDiagnosticVoucherRequest);

    /**
     * 특정 독서능력진단평가 바우처 조회(현재 로그인한 교육기관의 바우처만 조회 가능)
     * @param readingDiagnosticVoucherId 독서능력진단평가 바우처 고유번호
     * @return 특정 독서능력진단평가 바우처 응답 객체
     */
    ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherResponse get(Long readingDiagnosticVoucherId);
}