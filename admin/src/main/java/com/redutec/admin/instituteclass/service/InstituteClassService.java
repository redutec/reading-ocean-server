package com.redutec.admin.instituteclass.service;

import com.redutec.admin.instituteclass.dto.InstituteClassDto;
import com.redutec.core.entity.InstituteClass;

public interface InstituteClassService {
    /**
     * 학급 등록
     * @param createInstituteClassRequest 학급 등록 정보를 담은 DTO
     * @return 등록된 학급 정보
     */
    InstituteClassDto.InstituteClassResponse create(InstituteClassDto.CreateInstituteClassRequest createInstituteClassRequest);

    /**
     * 조건에 맞는 학급 목록 조회
     * @param findInstituteClassRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 학급 목록 및 페이징 정보
     */
    InstituteClassDto.InstituteClassPageResponse find(InstituteClassDto.FindInstituteClassRequest findInstituteClassRequest);

    /**
     * 특정 학급 조회
     * @param instituteClassId 학급 고유번호
     * @return 특정 학급 응답 객체
     */
    InstituteClassDto.InstituteClassResponse findById(Long instituteClassId);

    /**
     * 특정 학급 엔티티 조회
     * @param instituteClassId 학급 고유번호
     * @return 특정 학급 엔티티 객체
     */
    InstituteClass getInstituteClass(Long instituteClassId);

    /**
     * 특정 학급 수정
     * @param instituteClassId 수정할 학급의 ID
     * @param updateInstituteClassRequest 학급 수정 요청 객체
     */
    void update(Long instituteClassId, InstituteClassDto.UpdateInstituteClassRequest updateInstituteClassRequest);

    /**
     * 특정 학급 삭제
     * @param instituteClassId 삭제할 학급의 ID
     */
    void delete(Long instituteClassId);
}