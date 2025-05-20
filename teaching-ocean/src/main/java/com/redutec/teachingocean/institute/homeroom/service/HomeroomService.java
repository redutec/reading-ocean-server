package com.redutec.teachingocean.institute.homeroom.service;

import com.redutec.core.dto.HomeroomDto;

import java.util.List;

public interface HomeroomService {
    /**
     * 학사관리 - 학급관리 - 신규 학급 등록
     * @param createHomeroomRequest 학급 등록 정보를 담은 DTO
     * @return 등록된 학급 정보
     */
    HomeroomDto.HomeroomResponse create(HomeroomDto.CreateHomeroomRequest createHomeroomRequest);

    /**
     * 학사관리 - 학급관리 - 학급 목록 조회
     * @param findHomeroomRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 학급 목록 및 페이징 정보
     */
    HomeroomDto.HomeroomPageResponse find(HomeroomDto.FindHomeroomRequest findHomeroomRequest);

    /**
     * 학사관리 - 학급관리 - 특정 학급 조회
     * @param homeroomId 학급 고유번호
     * @return 특정 학급 응답 객체
     */
    HomeroomDto.HomeroomResponse findById(Long homeroomId);

    /**
     * 학사관리 - 학급관리 - 특정 학급 수정
     * @param homeroomId 수정할 학급의 고유번호
     * @param updateHomeroomRequest 학급 수정 요청 객체
     */
    void update(Long homeroomId, HomeroomDto.UpdateHomeroomRequest updateHomeroomRequest);

    /**
     * 학사관리 - 학급관리 - 선택 학급 삭제
     * @param homeroomIds 삭제할 학급의 고유번호 목록
     */
    void delete(List<Long> homeroomIds);
}