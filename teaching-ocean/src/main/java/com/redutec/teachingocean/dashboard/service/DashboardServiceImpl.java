package com.redutec.teachingocean.dashboard.service;

import com.redutec.core.dto.DashboardDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.meta.StudentStatus;
import com.redutec.core.meta.TeacherStatus;
import com.redutec.core.repository.BookRepository;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.StudentRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {
    private final AuthenticationService authenticationService;
    private final BookRepository bookRepository;
    private final InstituteRepository instituteRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    /**
     * 현재 로그인한 교사가 속한 교육기관의 대시보드 조회
     */
    @Override
    public DashboardDto.TeachingOceanDashboardResponse getDashboard() {
        // 리딩오션에 등록된 도서 목록을 조회
        var books = bookRepository.findByVisibleAndEnabled(true, true);
        // 현재 로그인한 교사가 속한 교육기관 엔티티 조회
        Long instituteId = authenticationService.getAuthenticatedTeacher().instituteId();
        Institute institute = instituteRepository.findById(instituteId)
                .orElseThrow(() -> new EntityNotFoundException("교육기관이 없습니다. instituteId: " + instituteId));
        // 교육기관에 등록된 교사 수 조회
        var teachers = teacherRepository.findByInstitute(institute);
        var activeTeachers = teachers.stream()
                .filter(teacher -> teacher.getStatus() == TeacherStatus.ACTIVE)
                .count();
        var inactiveTeachers = teachers.stream()
                .filter(teacher -> teacher.getStatus() == TeacherStatus.INACTIVE)
                .count();
        var waitingTeachers = teachers.stream()
                .filter(teacher -> teacher.getStatus() == TeacherStatus.WAIT)
                .count();
        // 교육기관에 등록된 학생 수 조회
        var students = studentRepository.findByInstitute(institute);
        var activeStudents = students.stream()
                .filter(student -> student.getStatus() == StudentStatus.ACTIVE)
                .count();
        var inactiveStudents = students.stream()
                .filter(student -> student.getStatus() == StudentStatus.INACTIVE)
                .count();
        var waitingStudents = students.stream()
                .filter(student -> student.getStatus() == StudentStatus.WAIT)
                .count();
        // 대시보드 응답 객체에 담아서 리턴
        return new DashboardDto.TeachingOceanDashboardResponse(
                teachers.size(),
                (int) activeTeachers,
                (int) inactiveTeachers,
                (int) waitingTeachers,
                students.size(),
                (int) activeStudents,
                (int) inactiveStudents,
                (int) waitingStudents,
                books.size()
        );
    }
}