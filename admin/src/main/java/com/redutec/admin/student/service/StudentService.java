package com.redutec.admin.student.service;

import com.redutec.admin.student.dto.StudentDto;

import java.util.List;

public interface StudentService {
    /**
     * 조건에 맞는 학생 계정 목록 조회 API
     *
     * @param findStudentRequest 학생 계정 조회 조건을 포함하는 DTO
     * @return 조회된 학생 계정 목록과 관련된 추가 정보를 포함하는 응답 객체
     */
    List<StudentDto.StudentResponse> find(StudentDto.FindStudentRequest findStudentRequest);
}
