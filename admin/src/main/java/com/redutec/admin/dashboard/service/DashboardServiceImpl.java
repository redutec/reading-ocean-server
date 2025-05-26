package com.redutec.admin.dashboard.service;

import com.redutec.core.dto.DashboardDto;
import com.redutec.core.meta.BranchStatus;
import com.redutec.core.meta.InstituteStatus;
import com.redutec.core.meta.StudentStatus;
import com.redutec.core.meta.TeacherStatus;
import com.redutec.core.repository.BranchRepository;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.StudentRepository;
import com.redutec.core.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {
    private final BranchRepository branchRepository;
    private final InstituteRepository instituteRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    /**
     * 어드민 대시보드 조회
     *
     * @return 어드민 대시보드 정보 조회(지사, 교육기관, 교사, 학생 상태별 조회)
     */
    @Override
    public DashboardDto.AdminDashboardResponse getDashboard() {
        // 지사 상태별 카운트
        var branches = branchRepository.findAll();
        int totalBranches = branches.size();
        int activeBranches = (int) branches.stream()
                .filter(b -> b.getStatus() == BranchStatus.ACTIVE)
                .count();
        int inactiveBranches = (int) branches.stream()
                .filter(b -> b.getStatus() == BranchStatus.INACTIVE)
                .count();
        // 교육기관 상태별 카운트
        var institutes = instituteRepository.findAll();
        int totalInstitutes = institutes.size();
        int activeInstitutes = (int) institutes.stream()
                .filter(i -> i.getStatus() == InstituteStatus.ACTIVE)
                .count();
        int waitInstitutes = (int) institutes.stream()
                .filter(i -> i.getStatus() == InstituteStatus.WAIT)
                .count();
        int nonPaymentInstitutes = (int) institutes.stream()
                .filter(i -> i.getStatus() == InstituteStatus.NON_PAYMENT)
                .count();
        int terminationInstitutes = (int) institutes.stream()
                .filter(i -> i.getStatus() == InstituteStatus.TERMINATION)
                .count();
        // 교사 상태별 카운트
        var teachers = teacherRepository.findAll();
        int totalTeachers = teachers.size();
        int activeTeachers = (int) teachers.stream()
                .filter(t -> t.getStatus() == TeacherStatus.ACTIVE)
                .count();
        int inactiveTeachers = (int) teachers.stream()
                .filter(t -> t.getStatus() == TeacherStatus.INACTIVE)
                .count();
        int waitTeachers = (int) teachers.stream()
                .filter(t -> t.getStatus() == TeacherStatus.WAIT)
                .count();
        // 학생 상태별 카운트
        var students = studentRepository.findAll();
        int totalStudents = students.size();
        int activeStudents = (int) students.stream()
                .filter(s -> s.getStatus() == StudentStatus.ACTIVE)
                .count();
        int inactiveStudents = (int) students.stream()
                .filter(s -> s.getStatus() == StudentStatus.INACTIVE)
                .count();
        int waitStudents = (int) students.stream()
                .filter(s -> s.getStatus() == StudentStatus.WAIT)
                .count();
        // DTO에 담아 반환
        return new DashboardDto.AdminDashboardResponse(
                totalBranches,
                activeBranches,
                inactiveBranches,
                totalInstitutes,
                activeInstitutes,
                waitInstitutes,
                nonPaymentInstitutes,
                terminationInstitutes,
                totalTeachers,
                activeTeachers,
                inactiveTeachers,
                waitTeachers,
                totalStudents,
                activeStudents,
                inactiveStudents,
                waitStudents
        );
    }
}