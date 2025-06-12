package com.redutec.admin.inquiry.teacher.service;

import com.redutec.core.dto.TeacherInquiryDto;
import com.redutec.core.entity.AdminUser;
import com.redutec.core.entity.TeacherInquiry;
import com.redutec.core.mapper.TeacherInquiryMapper;
import com.redutec.core.repository.AdminUserRepository;
import com.redutec.core.repository.TeacherInquiryRepository;
import com.redutec.core.specification.TeacherInquirySpecification;
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
public class TeacherInquiryServiceImpl implements TeacherInquiryService {
    private final TeacherInquiryMapper teacherInquiryMapper;
    private final TeacherInquiryRepository teacherInquiryRepository;
    private final AdminUserRepository adminUserRepository;

    /**
     * 조건에 맞는 고객문의(교사) 목록 조회
     * @param findTeacherInquiryRequest 조회 조건을 담은 DTO
     * @return 조회된 고객문의(교사) 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherInquiryDto.TeacherInquiryPageResponse find(TeacherInquiryDto.FindTeacherInquiryRequest findTeacherInquiryRequest) {
        return teacherInquiryMapper.toPageResponseDto(teacherInquiryRepository.findAll(
                TeacherInquirySpecification.findWith(teacherInquiryMapper.toCriteria(findTeacherInquiryRequest)),
                (findTeacherInquiryRequest.page() != null && findTeacherInquiryRequest.size() != null)
                        ? PageRequest.of(findTeacherInquiryRequest.page(), findTeacherInquiryRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 고객문의(교사) 조회
     * @param teacherInquiryId 고객문의(교사) 고유번호
     * @return 특정 고객문의(교사) 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherInquiryDto.TeacherInquiryResponse findById(Long teacherInquiryId) {
        return teacherInquiryMapper.toResponseDto(getTeacherInquiry(teacherInquiryId));
    }

    /**
     * 특정 고객문의(교사) 수정
     * @param teacherInquiryId 수정할 고객문의(교사)의 ID
     * @param updateTeacherInquiryRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long teacherInquiryId, TeacherInquiryDto.UpdateTeacherInquiryRequest updateTeacherInquiryRequest) {
        // 수정할 고객문의(교사) 엔티티 조회
        TeacherInquiry teacherInquiry = getTeacherInquiry(teacherInquiryId);
        // 수정 요청 객체에 답변자 아이디가 존재한다면 어드민 사용자 엔티티 조회
        AdminUser responder = Optional.ofNullable(updateTeacherInquiryRequest.responderAccountId())
                .flatMap(adminUserRepository::findByAccountId)
                .orElseGet(teacherInquiry::getResponder);
        // 고객문의(교사) 수정
        teacherInquiryRepository.save(teacherInquiryMapper.toUpdateEntity(
                teacherInquiry,
                responder,
                updateTeacherInquiryRequest
        ));
    }

    /**
     * 특정 고객문의(교사) 삭제
     * @param teacherInquiryId 삭제할 고객문의(교사)의 ID
     */
    @Override
    @Transactional
    public void delete(Long teacherInquiryId) {
        teacherInquiryRepository.delete(getTeacherInquiry(teacherInquiryId));
    }

    /**
     * 특정 고객문의(교사) 엔티티 조회
     * @param teacherInquiryId 고객문의(교사) 고유번호
     * @return 특정 고객문의(교사) 엔티티 객체
     */
    @Transactional(readOnly = true)
    public TeacherInquiry getTeacherInquiry(Long teacherInquiryId) {
        return teacherInquiryRepository.findById(teacherInquiryId)
                .orElseThrow(() -> new EntityNotFoundException("고객문의(교사)를 찾을 수 없습니다. teacherInquiryId: " + teacherInquiryId));
    }
}