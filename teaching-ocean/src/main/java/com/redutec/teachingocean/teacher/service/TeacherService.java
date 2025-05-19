package com.redutec.teachingocean.teacher.service;

import com.redutec.core.entity.Teacher;
import com.redutec.core.dto.TeacherDto;

public interface TeacherService {
    /**
     * 교사 등록
     * @param createTeacherRequest 교사 등록 정보를 담은 DTO
     * @return 등록된 교사 정보
     */
    TeacherDto.TeacherResponse create(TeacherDto.CreateTeacherRequest createTeacherRequest);

    /**
     * 조건에 맞는 교사 목록 조회
     * @param findTeacherRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 교사 목록 및 페이징 정보
     */
    TeacherDto.TeacherPageResponse find(TeacherDto.FindTeacherRequest findTeacherRequest);

    /**
     * 특정 교사 조회
     * @param teacherId 교사 고유번호
     * @return 특정 교사 응답 객체
     */
    TeacherDto.TeacherResponse findById(Long teacherId);

    /**
     * 특정 교사 엔티티 조회
     * @param accountId 교사의 로그인 아이디
     * @return 특정 교사 엔티티 객체
     */
    Teacher findByAccountId(String accountId);

    /**
     * 특정 교사 엔티티 조회
     * @param teacherId 교사 고유번호
     * @return 특정 교사 엔티티 객체
     */
    Teacher getTeacher(Long teacherId);

    /**
     * 특정 교사 수정
     * @param teacherId 수정할 교사의 ID
     * @param updateTeacherRequest 교사 수정 요청 객체
     */
    void update(Long teacherId, TeacherDto.UpdateTeacherRequest updateTeacherRequest);
}