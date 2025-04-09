package com.redutec.admin.student.service;

import com.redutec.admin.student.dto.StudentDto;
import com.redutec.core.entity.ActAcademy;
import com.redutec.core.repository.ActAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final ActAccountRepository actAccountRepository;

    /**
     * 조건에 맞는 학생 계정 목록 조회 API
     *
     * @param findStudentRequest 학생 계정 조회 조건을 포함하는 DTO
     * @return 조회된 학생 계정 목록과 관련된 추가 정보를 포함하는 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentDto.StudentResponse> find(StudentDto.FindStudentRequest findStudentRequest) {
        var actAccountEntityList = actAccountRepository.findAll();
        return actAccountEntityList.stream()
                .map(student -> {
                    // null 여부 체크 및 academy 정보 추출
                    ActAcademy academy = student.getAcademy();
                    Integer academyNo = academy != null ? academy.getAcademyNo() : null;
                    String academyName = academy != null ? academy.getAcademyName() : null;
                    return new StudentDto.StudentResponse(
                            student.getAccountNo(),
                            student.getAccountId(),
                            student.getEmail(),
                            student.getName(),
                            student.getMobileNo(),
                            student.getSchoolGrade(),
                            student.getAccountStatus(),
                            student.getSignupDomainCode(),
                            academyNo,
                            academyName
                    );
                })
                .toList();
    }
}