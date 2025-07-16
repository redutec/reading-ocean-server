package com.redutec.admin.inquiry.student.service;

import com.redutec.core.dto.StudentInquiryDto;
import com.redutec.core.entity.AdminUser;
import com.redutec.core.entity.StudentInquiry;
import com.redutec.core.mapper.StudentInquiryMapper;
import com.redutec.core.repository.AdminUserRepository;
import com.redutec.core.repository.StudentInquiryRepository;
import com.redutec.core.specification.StudentInquirySpecification;
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
public class StudentInquiryServiceImpl implements StudentInquiryService {
    private final StudentInquiryMapper studentInquiryMapper;
    private final StudentInquiryRepository studentInquiryRepository;
    private final AdminUserRepository adminUserRepository;

    /**
     * 조건에 맞는 고객문의(학생) 목록 조회
     * @param findStudentInquiryRequest 조회 조건을 담은 DTO
     * @return 조회된 고객문의(학생) 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public StudentInquiryDto.StudentInquiryPageResponse find(StudentInquiryDto.FindStudentInquiryRequest findStudentInquiryRequest) {
        return studentInquiryMapper.toPageResponseDto(studentInquiryRepository.findAll(
                StudentInquirySpecification.findWith(studentInquiryMapper.toCriteria(findStudentInquiryRequest)),
                (findStudentInquiryRequest.page() != null && findStudentInquiryRequest.size() != null)
                        ? PageRequest.of(findStudentInquiryRequest.page(), findStudentInquiryRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 고객문의(학생) 조회
     * @param studentInquiryId 고객문의(학생) 고유번호
     * @return 특정 고객문의(학생) 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public StudentInquiryDto.StudentInquiryResponse get(Long studentInquiryId) {
        return studentInquiryMapper.toResponseDto(getStudentInquiry(studentInquiryId));
    }

    /**
     * 특정 고객문의(학생) 수정
     * @param studentInquiryId 수정할 고객문의(학생)의 ID
     * @param updateStudentInquiryRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long studentInquiryId, StudentInquiryDto.UpdateStudentInquiryRequest updateStudentInquiryRequest) {
        // 수정할 고객문의(학생) 엔티티 조회
        StudentInquiry studentInquiry = getStudentInquiry(studentInquiryId);
        // 수정 요청 객체에 답변자 아이디가 존재한다면 어드민 사용자 엔티티 조회
        AdminUser responder = Optional.ofNullable(updateStudentInquiryRequest.responderAccountId())
                .flatMap(adminUserRepository::findByAccountId)
                .orElseGet(studentInquiry::getResponder);
        // 고객문의(학생) 수정
        studentInquiryMapper.updateEntity(
                studentInquiry,
                updateStudentInquiryRequest,
                responder
        );
    }

    /**
     * 특정 고객문의(학생) 삭제
     * @param studentInquiryId 삭제할 고객문의(학생)의 ID
     */
    @Override
    @Transactional
    public void delete(Long studentInquiryId) {
        studentInquiryRepository.delete(getStudentInquiry(studentInquiryId));
    }

    /**
     * 특정 고객문의(학생) 엔티티 조회
     * @param studentInquiryId 고객문의(학생) 고유번호
     * @return 특정 고객문의(학생) 엔티티 객체
     */
    @Transactional(readOnly = true)
    public StudentInquiry getStudentInquiry(Long studentInquiryId) {
        return studentInquiryRepository.findById(studentInquiryId)
                .orElseThrow(() -> new EntityNotFoundException("고객문의(학생)를 찾을 수 없습니다. studentInquiryId: " + studentInquiryId));
    }
}