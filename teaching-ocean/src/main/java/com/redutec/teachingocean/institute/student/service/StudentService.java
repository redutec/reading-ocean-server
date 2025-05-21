package com.redutec.teachingocean.institute.student.service;

import com.redutec.core.dto.StudentDto;

public interface StudentService {
    /**
     * 학사관리 - 학생 관리 - 신규 학생 등록
     * @param createStudentRequest 학생 등록 정보를 담은 DTO
     * @return 등록된 학생 정보
     */
    StudentDto.StudentResponse create(StudentDto.CreateStudentRequest createStudentRequest);

    /**
     * 학사관리 - 학생 관리 - 학생 목록 조회
     * @param findStudentRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 학생 목록 및 페이징 정보
     */
    StudentDto.StudentPageResponse find(StudentDto.FindStudentRequest findStudentRequest);

    /**
     * 학사관리 - 학생 관리 - 특정 학생 조회
     * @param studentId 학생 고유번호
     * @return 특정 학생 응답 객체
     */
    StudentDto.StudentResponse findById(Long studentId);

    /**
     * 학사관리 - 학생 관리 - 특정 학생 수정
     * @param studentId 수정할 학생의 고유번호
     * @param updateStudentRequest 학생 수정 요청 객체
     */
    void update(Long studentId, StudentDto.UpdateStudentRequest updateStudentRequest);
}