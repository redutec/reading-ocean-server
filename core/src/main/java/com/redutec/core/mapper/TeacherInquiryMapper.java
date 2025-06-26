package com.redutec.core.mapper;

import com.redutec.core.criteria.TeacherInquiryCriteria;
import com.redutec.core.dto.TeacherInquiryDto;
import com.redutec.core.entity.AdminUser;
import com.redutec.core.entity.Teacher;
import com.redutec.core.entity.TeacherInquiry;
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
public class TeacherInquiryMapper {
    /**
     * CreateInquiryRequest DTO를 기반으로 TeacherInquiry 등록 엔티티를 생성합니다.
     *
     * @param createTeacherInquiryRequest TeacherInquiry 등록에 필요한 데이터를 담은 DTO
     * @param teacher 고객문의를 등록한 교사
     * @return 등록할 TeacherInquiry 엔티티
     */
    public TeacherInquiry createEntity(
            TeacherInquiryDto.CreateTeacherInquiryRequest createTeacherInquiryRequest,
            Teacher teacher
    ) {
        return TeacherInquiry.builder()
                .teacher(teacher)
                .domain(createTeacherInquiryRequest.domain())
                .category(createTeacherInquiryRequest.category())
                .status(createTeacherInquiryRequest.status())
                .title(StringUtils.stripToNull(createTeacherInquiryRequest.title()))
                .content(StringUtils.stripToNull(createTeacherInquiryRequest.content()))
                .build();
    }

    /**
     * UpdateInquiryRequest DTO를 기반으로 TeacherInquiry 엔티티를 수정합니다.
     *
     * @param teacherInquiry 수정할 TeacherInquiry 엔티티
     * @param updateTeacherInquiryRequest TeacherInquiry 수정에 필요한 데이터를 담은 DTO
     * @param responder 문의에 답변한 어드민 사용자 엔티티
     */
    public void updateEntity(
            TeacherInquiry teacherInquiry,
            AdminUser responder,
            TeacherInquiryDto.UpdateTeacherInquiryRequest updateTeacherInquiryRequest
    ) {
        Optional.ofNullable(updateTeacherInquiryRequest.domain())
                .ifPresent(teacherInquiry::setDomain);
        Optional.ofNullable(updateTeacherInquiryRequest.category())
                .ifPresent(teacherInquiry::setCategory);
        Optional.ofNullable(updateTeacherInquiryRequest.status())
                .ifPresent(teacherInquiry::setStatus);
        Optional.ofNullable(responder)
                .ifPresent(teacherInquiry::setResponder);
        Optional.ofNullable(StringUtils.stripToNull(updateTeacherInquiryRequest.title()))
                .ifPresent(teacherInquiry::setTitle);
        Optional.ofNullable(StringUtils.stripToNull(updateTeacherInquiryRequest.content()))
                .ifPresent(teacherInquiry::setContent);
        Optional.ofNullable(StringUtils.stripToNull(updateTeacherInquiryRequest.response()))
                .ifPresent(teacherInquiry::setResponse);
    }
    
    /**
     * 이 메서드는 현재 FindTeacherInquiryRequest 객체를 기반으로
     * TeacherInquiryCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 고객문의 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 InquiryCriteria 객체
     */
    public TeacherInquiryCriteria toCriteria(TeacherInquiryDto.FindTeacherInquiryRequest findTeacherInquiryRequest) {
        return new TeacherInquiryCriteria(
                findTeacherInquiryRequest.teacherInquiryIds(),
                findTeacherInquiryRequest.domains(),
                findTeacherInquiryRequest.categories(),
                findTeacherInquiryRequest.statuses(),
                findTeacherInquiryRequest.inquirerAccountId(),
                findTeacherInquiryRequest.responderAccountId(),
                StringUtils.stripToNull(findTeacherInquiryRequest.title()),
                StringUtils.stripToNull(findTeacherInquiryRequest.content()),
                StringUtils.stripToNull(findTeacherInquiryRequest.response())
        );
    }

    /**
     * TeacherInquiry 엔티티를 기반으로 응답용 InquiryResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param teacherInquiry 변환할 TeacherInquiry 엔티티
     * @return Inquiry 엔티티의 데이터를 담은 InquiryResponse DTO, inquiry가 null이면 null 반환
     */
    public TeacherInquiryDto.TeacherInquiryResponse toResponseDto(TeacherInquiry teacherInquiry) {
        return Optional.ofNullable(teacherInquiry)
                .map(ti -> new TeacherInquiryDto.TeacherInquiryResponse(
                        ti.getId(),
                        ti.getDomain(),
                        ti.getCategory(),
                        ti.getStatus(),
                        ti.getTeacher().getAccountId(),
                        Optional.ofNullable(teacherInquiry.getResponder())
                                .map(AdminUser::getAccountId)
                                .orElse(null),
                        ti.getTitle(),
                        ti.getContent(),
                        ti.getCreatedAt(),
                        ti.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 TeacherInquiry 엔티티 목록을 TeacherInquiryPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param teacherInquiryPage Page 형태로 조회된 TeacherInquiry 엔티티 목록 (null 가능)
     * @return Inquiry 엔티티 리스트와 페이지 정보를 담은 TeacherInquiryPageResponse DTO, inquiryPage가 null이면 null 반환
     */
    public TeacherInquiryDto.TeacherInquiryPageResponse toPageResponseDto(Page<TeacherInquiry> teacherInquiryPage) {
        return Optional.ofNullable(teacherInquiryPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new TeacherInquiryDto.TeacherInquiryPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}