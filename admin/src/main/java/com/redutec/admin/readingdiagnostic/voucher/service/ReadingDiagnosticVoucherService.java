package com.redutec.admin.readingdiagnostic.voucher.service;

import com.redutec.core.dto.ReadingDiagnosticVoucherDto;

public interface ReadingDiagnosticVoucherService {
    /**
     * 독서능력진단평가 바우처 등록
     * @param createReadingDiagnosticVoucherRequest 독서능력진단평가 바우처 등록 정보를 담은 DTO
     * @return 등록된 독서능력진단평가 바우처 정보
     */
    ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherResponse create(ReadingDiagnosticVoucherDto.CreateReadingDiagnosticVoucherRequest createReadingDiagnosticVoucherRequest);

    /**
     * 조건에 맞는 독서능력진단평가 바우처 목록 조회
     * @param findReadingDiagnosticVoucherRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 독서능력진단평가 바우처 목록 및 페이징 정보
     */
    ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherPageResponse find(ReadingDiagnosticVoucherDto.FindReadingDiagnosticVoucherRequest findReadingDiagnosticVoucherRequest);

    /**
     * 특정 독서능력진단평가 바우처 조회
     * @param readingDiagnosticVoucherId 독서능력진단평가 바우처 고유번호
     * @return 특정 독서능력진단평가 바우처 응답 객체
     */
    ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherResponse get(Long readingDiagnosticVoucherId);

    /**
     * 특정 독서능력진단평가 바우처 수정
     * @param readingDiagnosticVoucherId 수정할 독서능력진단평가 바우처의 ID
     * @param updateReadingDiagnosticVoucherRequest 독서능력진단평가 바우처 수정 요청 객체
     */
    void update(Long readingDiagnosticVoucherId, ReadingDiagnosticVoucherDto.UpdateReadingDiagnosticVoucherRequest updateReadingDiagnosticVoucherRequest);

    /**
     * 특정 독서능력진단평가 바우처 삭제
     * @param readingDiagnosticVoucherId 삭제할 독서능력진단평가 바우처의 ID
     */
    void delete(Long readingDiagnosticVoucherId);
}