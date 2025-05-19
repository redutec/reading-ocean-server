package com.redutec.teachingocean.institute.service;

import com.redutec.core.dto.InstituteDto;
import com.redutec.core.entity.Institute;

public interface InstituteService {
    /**
     * 현재 로그인한 교사가 속한 교육기관 조회
     * @return 특정 교육기관 응답 객체
     */
    InstituteDto.InstituteResponse findInstitute();

    /**
     * 특정 교육기관 엔티티 조회
     * @param instituteId 교육기관 고유번호
     * @return 특정 교육기관 엔티티 객체
     */
    Institute getInstitute(Long instituteId);

    /**
     * 현재 로그인한 교사가 속한 교육기관 수정
     * @param updateInstituteRequest 교육기관 수정 요청 객체
     */
    void update(InstituteDto.UpdateInstituteRequest updateInstituteRequest);
}