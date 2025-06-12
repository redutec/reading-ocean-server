package com.redutec.teachingocean.inquiry.service;

import com.redutec.core.dto.TeacherInquiryDto;
import com.redutec.core.entity.AdminUser;
import com.redutec.core.entity.Teacher;
import com.redutec.core.entity.TeacherInquiry;
import com.redutec.core.mapper.TeacherInquiryMapper;
import com.redutec.core.repository.AdminUserRepository;
import com.redutec.core.repository.TeacherInquiryRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.specification.TeacherInquirySpecification;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class InquiryServiceImpl implements InquiryService {
    private final TeacherInquiryMapper teacherInquiryMapper;
    private final TeacherInquiryRepository teacherInquiryRepository;
    private final TeacherRepository teacherRepository;
    private final AdminUserRepository adminUserRepository;
    private final AuthenticationService authenticationService;

    /**
     * 고객센터 - 1:1 문의 - 고객문의 등록
     * @param createTeacherInquiryRequest 고객문의 등록 정보를 담은 DTO
     * @return 등록된 고객문의 정보
     */
    @Override
    @Transactional
    public TeacherInquiryDto.TeacherInquiryResponse create(
            TeacherInquiryDto.CreateTeacherInquiryRequest createTeacherInquiryRequest
    ) {
        // 현재 로그인한 교사의 엔티티 조회
        Teacher teacher = teacherRepository.findById(authenticationService.getAuthenticatedTeacher().teacherId())
                .orElseThrow(() -> new EntityNotFoundException("교사가 존재하지 않습니다. teacherId: " + authenticationService.getAuthenticatedTeacher().teacherId()));
        // 고객문의 등록
        return teacherInquiryMapper.toResponseDto(teacherInquiryRepository.save(teacherInquiryMapper.toCreateEntity(
                createTeacherInquiryRequest,
                teacher
        )));
    }

    /**
     * 고객센터 - 1:1 문의 - 고객문의 목록 조회
     * @param findTeacherInquiryRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교사가 등록한 고객문의
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherInquiryDto.TeacherInquiryPageResponse find(
            TeacherInquiryDto.FindTeacherInquiryRequest findTeacherInquiryRequest
    ) {
        // 인증된 교사의 계정으로 inquirerAccountId를 덮어쓴 새로운 request 생성
        var request = findTeacherInquiryRequest.setAuthenticatedInquirer(
                authenticationService.getAuthenticatedTeacher().accountId()
        );
        // 고객문의 목록 조회
        return teacherInquiryMapper.toPageResponseDto(teacherInquiryRepository.findAll(
                TeacherInquirySpecification.findWith(teacherInquiryMapper.toCriteria(request)),
                (request.page() != null && request.size() != null)
                        ? PageRequest.of(request.page(), request.size())
                        : Pageable.unpaged()
        ));
    }

    /**
     * 고객센터 - 1:1 문의 - 특정 고객문의 조회
     * @param teacherInquiryId 고객문의 고유번호
     * @return 특정 고객문의 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherInquiryDto.TeacherInquiryResponse findById(Long teacherInquiryId) {
        // 현재 로그인한 교사의 로그인 아이디 조회
        String currentAccountId = authenticationService.getAuthenticatedTeacher().accountId();
        // 해당 고객문의의 문의자(교사)와 현재 로그인한 교사가 동일한 엔티티인지 조회 후 아니면 존재하지 않는 고객문의라고 리턴
        return Optional.of(getTeacherInquiry(teacherInquiryId))
                .filter(ti -> ti.getTeacher().getAccountId().equals(currentAccountId))
                .map(teacherInquiryMapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("고객문의를 찾을 수 없습니다. teacherInquiryId: " + teacherInquiryId));
    }

    /**
     * 고객센터 - 1:1 문의 - 특정 고객문의 수정
     * @param teacherInquiryId 수정할 고객문의의 고유번호
     * @param updateTeacherInquiryRequest 고객문의 수정 요청 객체
     */
    @Override
    @Transactional
    public void update(
            Long teacherInquiryId,
            TeacherInquiryDto.UpdateTeacherInquiryRequest updateTeacherInquiryRequest
    ) {
        // 수정할 고객문의 엔티티 조회
        TeacherInquiry teacherInquiry = getTeacherInquiry(teacherInquiryId);
        // 수정 요청 객체에 답변자 아이디가 존재한다면 어드민 사용자 엔티티 조회
        AdminUser responder = Optional.ofNullable(updateTeacherInquiryRequest.responderAccountId())
                .flatMap(adminUserRepository::findByAccountId)
                .orElseGet(teacherInquiry::getResponder);
        // 고객문의 수정
        teacherInquiryRepository.save(teacherInquiryMapper.toUpdateEntity(
                teacherInquiry,
                responder,
                updateTeacherInquiryRequest
        ));
    }

    /**
     * 특정 고객문의(교사) 엔티티 조회
     * @param teacherInquiryId 고객문의(교사) 고유번호
     * @return 특정 고객문의(교사) 엔티티 객체
     */
    @Transactional(readOnly = true)
    public TeacherInquiry getTeacherInquiry(Long teacherInquiryId) {
        return teacherInquiryRepository.findById(teacherInquiryId)
                .orElseThrow(() -> new EntityNotFoundException("고객문의를 찾을 수 없습니다. teacherInquiryId: " + teacherInquiryId));
    }
}