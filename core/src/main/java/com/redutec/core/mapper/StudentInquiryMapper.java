package com.redutec.core.mapper;

import com.redutec.core.criteria.StudentInquiryCriteria;
import com.redutec.core.dto.StudentInquiryDto;
import com.redutec.core.entity.AdminUser;
import com.redutec.core.entity.Student;
import com.redutec.core.entity.StudentInquiry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class StudentInquiryMapper {
    /**
     * CreateInquiryRequest DTO를 기반으로 StudentInquiry 등록 엔티티를 생성합니다.
     * @param createStudentInquiryRequest StudentInquiry 등록에 필요한 데이터를 담은 DTO
     * @param student 고객문의를 등록한 교사
     * @return 등록할 StudentInquiry 엔티티
     */
    public StudentInquiry createEntity(
            StudentInquiryDto.CreateStudentInquiryRequest createStudentInquiryRequest,
            Student student
    ) {
        return StudentInquiry.builder()
                .student(student)
                .domain(createStudentInquiryRequest.domain())
                .category(createStudentInquiryRequest.category())
                .status(createStudentInquiryRequest.status())
                .title(StringUtils.stripToNull(createStudentInquiryRequest.title()))
                .content(StringUtils.stripToNull(createStudentInquiryRequest.content()))
                .build();
    }

    /**
     * UpdateInquiryRequest DTO를 기반으로 StudentInquiry 엔티티를 수정합니다.
     * @param studentInquiry 수정할 StudentInquiry 엔티티
     * @param updateStudentInquiryRequest StudentInquiry 수정에 필요한 데이터를 담은 DTO
     * @param responder 문의에 답변한 어드민 사용자 엔티티
     */
    public void updateEntity(
            StudentInquiry studentInquiry,
            StudentInquiryDto.UpdateStudentInquiryRequest updateStudentInquiryRequest,
            AdminUser responder
    ) {
        Optional.ofNullable(updateStudentInquiryRequest.domain())
                .ifPresent(studentInquiry::setDomain);
        Optional.ofNullable(updateStudentInquiryRequest.category())
                .ifPresent(studentInquiry::setCategory);
        Optional.ofNullable(updateStudentInquiryRequest.status())
                .ifPresent(studentInquiry::setStatus);
        Optional.ofNullable(responder)
                .ifPresent(studentInquiry::setResponder);
        Optional.ofNullable(StringUtils.stripToNull(updateStudentInquiryRequest.title()))
                .ifPresent(studentInquiry::setTitle);
        Optional.ofNullable(StringUtils.stripToNull(updateStudentInquiryRequest.content()))
                .ifPresent(studentInquiry::setContent);
        Optional.ofNullable(StringUtils.stripToNull(updateStudentInquiryRequest.response()))
                .ifPresent(studentInquiry::setResponse);
    }
    
    /**
     * 이 메서드는 현재 FindStudentInquiryRequest 객체를 기반으로
     * StudentInquiryCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 고객문의 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 InquiryCriteria 객체
     */
    public StudentInquiryCriteria toCriteria(StudentInquiryDto.FindStudentInquiryRequest findStudentInquiryRequest) {
        return new StudentInquiryCriteria(
                findStudentInquiryRequest.studentInquiryIds(),
                findStudentInquiryRequest.domains(),
                findStudentInquiryRequest.categories(),
                findStudentInquiryRequest.statuses(),
                findStudentInquiryRequest.inquirerAccountId(),
                findStudentInquiryRequest.responderAccountId(),
                findStudentInquiryRequest.title(),
                findStudentInquiryRequest.content(),
                findStudentInquiryRequest.response()
        );
    }

    /**
     * StudentInquiry 엔티티를 기반으로 응답용 InquiryResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param studentInquiry 변환할 StudentInquiry 엔티티
     * @return Inquiry 엔티티의 데이터를 담은 InquiryResponse DTO, inquiry가 null이면 null 반환
     */
    public StudentInquiryDto.StudentInquiryResponse toResponseDto(StudentInquiry studentInquiry) {
        return Optional.ofNullable(studentInquiry)
                .map(si -> new StudentInquiryDto.StudentInquiryResponse(
                        si.getId(),
                        si.getDomain(),
                        si.getCategory(),
                        si.getStatus(),
                        si.getStudent().getAccountId(),
                        si.getResponder().getAccountId(),
                        si.getTitle(),
                        si.getContent(),
                        si.getCreatedAt(),
                        si.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 StudentInquiry 엔티티 목록을 StudentInquiryPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param studentInquiryPage Page 형태로 조회된 StudentInquiry 엔티티 목록 (null 가능)
     * @return Inquiry 엔티티 리스트와 페이지 정보를 담은 StudentInquiryPageResponse DTO, inquiryPage가 null이면 null 반환
     */
    public StudentInquiryDto.StudentInquiryPageResponse toPageResponseDto(Page<StudentInquiry> studentInquiryPage) {
        return Optional.ofNullable(studentInquiryPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new StudentInquiryDto.StudentInquiryPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}