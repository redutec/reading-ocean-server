package com.redutec.admin.student.service;

import com.redutec.admin.student.dto.StudentDto;
import com.redutec.core.entity.Student;

public interface StudentService {
    /**
     * 학생 등록
     * @param createStudentRequest 학생 등록 정보를 담은 DTO
     * @return 등록된 학생 정보
     */
    StudentDto.StudentResponse create(StudentDto.CreateStudentRequest createStudentRequest);

    /**
     * 조건에 맞는 학생 목록 조회
     * @param findStudentRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 학생 목록 및 페이징 정보
     */
    StudentDto.StudentPageResponse find(StudentDto.FindStudentRequest findStudentRequest);

    /**
     * 특정 학생 조회
     * @param studentId 학생 고유번호
     * @return 특정 학생 응답 객체
     */
    StudentDto.StudentResponse findById(Long studentId);

    /**
     * 특정 학생 엔티티 조회
     * @param studentId 학생 고유번호
     * @return 특정 학생 엔티티 객체
     */
    Student getStudent(Long studentId);

    /**
     * 특정 학생 수정
     * @param studentId 수정할 학생의 ID
     * @param updateStudentRequest 학생 수정 요청 객체
     */
    void update(Long studentId, StudentDto.UpdateStudentRequest updateStudentRequest);

    /**
     * 특정 학생 삭제
     * @param studentId 삭제할 학생의 ID
     */
    void delete(Long studentId);
}