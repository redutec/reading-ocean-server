package com.redutec.teachingocean.institute.service;

import com.redutec.teachingocean.institute.dto.InstituteDto;
import com.redutec.core.entity.Institute;

public interface InstituteService {
    /**
     * 교육기관 등록
     * @param createInstituteRequest 교육기관 등록 정보를 담은 DTO
     * @return 등록된 교육기관 정보
     */
    InstituteDto.InstituteResponse create(InstituteDto.CreateInstituteRequest createInstituteRequest);

    /**
     * 조건에 맞는 교육기관 목록 조회
     * @param findInstituteRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 교육기관 목록 및 페이징 정보
     */
    InstituteDto.InstitutePageResponse find(InstituteDto.FindInstituteRequest findInstituteRequest);

    /**
     * 특정 교육기관 조회
     * @param instituteId 교육기관 고유번호
     * @return 특정 교육기관 응답 객체
     */
    InstituteDto.InstituteResponse findById(Long instituteId);

    /**
     * 특정 교육기관 엔티티 조회
     * @param instituteId 교육기관 고유번호
     * @return 특정 교육기관 엔티티 객체
     */
    Institute getInstitute(Long instituteId);

    /**
     * 특정 교육기관 수정
     * @param instituteId 수정할 교육기관의 ID
     * @param updateInstituteRequest 교육기관 수정 요청 객체
     */
    void update(Long instituteId, InstituteDto.UpdateInstituteRequest updateInstituteRequest);

    /**
     * 특정 교육기관 삭제
     * @param instituteId 삭제할 교육기관의 ID
     */
    void delete(Long instituteId);
}