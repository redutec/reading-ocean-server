package com.redutec.teachingocean.institute.service;

import com.redutec.core.dto.InstituteDto;

public interface InstituteService {
    /**
     * 마이페이지 - 교육기관 조회
     * @return 현재 로그인한 교사가 속한 교육기관 응답 객체
     */
    InstituteDto.InstituteResponse findOne();

    /**
     * 마이페이지 - 교육기관 수정
     * @param updateInstituteRequest 교육기관 수정 요청 객체
     */
    void update(InstituteDto.UpdateInstituteRequest updateInstituteRequest);
}