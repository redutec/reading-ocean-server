package com.redutec.teachingocean.meteredbilling.service;

import com.redutec.core.dto.MeteredBillingDto;

public interface MeteredBillingService {
    /**
     * 현재 로그인한 교사가 속한 교육기관의 월별 사용료 청구서 목록 조회
     * @param findMeteredBillingRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교사가 속한 교육기관의 월별 사용료 청구서 목록 및 페이징 정보
     */
    MeteredBillingDto.MeteredBillingPageResponse find(MeteredBillingDto.FindMeteredBillingRequest findMeteredBillingRequest);

    /**
     * 현재 로그인한 교사가 속한 교육기관의 특정 월별 사용료 청구서 조회
     * @param meteredBillingId 월별 사용료 청구서 고유번호
     * @return 특정 월별 사용료 청구서 응답 객체
     */
    MeteredBillingDto.MeteredBillingResponse get(Long meteredBillingId);
}