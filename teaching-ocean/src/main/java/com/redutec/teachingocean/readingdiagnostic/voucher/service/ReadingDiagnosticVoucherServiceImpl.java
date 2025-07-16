package com.redutec.teachingocean.readingdiagnostic.voucher.service;

import com.redutec.core.dto.ReadingDiagnosticVoucherDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.ReadingDiagnosticVoucher;
import com.redutec.core.mapper.ReadingDiagnosticVoucherMapper;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.ReadingDiagnosticVoucherRepository;
import com.redutec.core.specification.ReadingDiagnosticVoucherSpecification;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import com.redutec.teachingocean.readingdiagnostic.ticket.service.ReadingDiagnosticTicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ReadingDiagnosticVoucherServiceImpl implements ReadingDiagnosticVoucherService {
    private final ReadingDiagnosticVoucherMapper readingDiagnosticVoucherMapper;
    private final ReadingDiagnosticVoucherRepository readingDiagnosticVoucherRepository;
    private final InstituteRepository instituteRepository;
    private final ReadingDiagnosticTicketService readingDiagnosticTicketService;
    private final AuthenticationService authenticationService;

    /**
     * 조건에 맞는 독서능력진단평가 바우처 목록 조회
     *
     * @param findReadingDiagnosticVoucherRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 독서능력진단평가 바우처 목록 및 페이징 정보
     */
    @Override
    public ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherPageResponse find(
            ReadingDiagnosticVoucherDto.FindReadingDiagnosticVoucherRequest findReadingDiagnosticVoucherRequest
    ) {
        // 페이지 번호(page)와 페이지 크기(size)가 모두 null이 아니면 PageRequest 생성(그렇지 않으면 pageable 처리 하지 않음)
        Pageable pageable = Optional.ofNullable(findReadingDiagnosticVoucherRequest.page())
                .flatMap(page -> Optional.ofNullable(findReadingDiagnosticVoucherRequest.size())
                        .map(size -> (Pageable) PageRequest.of(page, size)))
                .orElse(Pageable.unpaged());
        // 현재 로그인한 교사의 교육기관 고유번호 Criteria에 적용하여 새로운 레코드로 생성
        var filteredRequest = new ReadingDiagnosticVoucherDto.FindReadingDiagnosticVoucherRequest(
                findReadingDiagnosticVoucherRequest.readingDiagnosticVoucherIds(),
                List.of(authenticationService.getAuthenticatedTeacher().instituteId()),
                findReadingDiagnosticVoucherRequest.name(),
                findReadingDiagnosticVoucherRequest.description(),
                findReadingDiagnosticVoucherRequest.page(),
                findReadingDiagnosticVoucherRequest.size()
        );
        // Specification, pageable을 사용하여 엔티티 목록 조회
        var readingDiagnosticVouchers = readingDiagnosticVoucherRepository.findAll(
                ReadingDiagnosticVoucherSpecification.findWith(readingDiagnosticVoucherMapper.toCriteria(filteredRequest)),
                pageable
        );
        // 조회한 엔티티 목록을 응답 객체로 변환하여 리턴
        return readingDiagnosticVoucherMapper.toPageResponseDto(readingDiagnosticVouchers);
    }

    /**
     * 특정 독서능력진단평가 바우처 조회
     *
     * @param readingDiagnosticVoucherId 독서능력진단평가 바우처 고유번호
     * @return 특정 독서능력진단평가 바우처 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherResponse get(Long readingDiagnosticVoucherId) {
        return readingDiagnosticVoucherMapper.toResponseDto(getReadingDiagnosticVoucher(readingDiagnosticVoucherId));
    }

    /**
     * 특정 독서능력진단평가 바우처 엔티티 조회(현재 로그인한 교사의 교육기관이 보유하지 않았으면 Null로 응답)
     * @param readingDiagnosticVoucherId 독서능력진단평가 바우처 고유번호
     * @return 특정 독서능력진단평가 바우처 엔티티 객체
     */
    @Transactional(readOnly = true)
    public ReadingDiagnosticVoucher getReadingDiagnosticVoucher(Long readingDiagnosticVoucherId) {
        // 현재 로그인한 교사의 교육기관 엔티티 조회
        Long instituteId = authenticationService.getAuthenticatedTeacher().instituteId();
        Institute institute = instituteRepository.findById(instituteId)
                .orElseThrow(() -> new EntityNotFoundException("교육기관이 존재하지 않습니다. instituteId: " + instituteId));
        // 독서능력진단평가 바우처 엔티티 조회 후 리턴
        return readingDiagnosticVoucherRepository.findByIdAndInstitute(readingDiagnosticVoucherId, institute)
                .orElseThrow(() -> new EntityNotFoundException("독서능력진단평가 바우처를 찾을 수 없습니다. readingDiagnosticVoucherId: " + readingDiagnosticVoucherId));
    }
}