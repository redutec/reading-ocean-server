package com.redutec.admin.reading.diagnostic.voucher.service;

import com.redutec.admin.reading.diagnostic.ticket.service.ReadingDiagnosticTicketService;
import com.redutec.core.dto.ReadingDiagnosticVoucherDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.ReadingDiagnosticVoucher;
import com.redutec.core.mapper.ReadingDiagnosticVoucherMapper;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.ReadingDiagnosticVoucherRepository;
import com.redutec.core.specification.ReadingDiagnosticVoucherSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Service
@Slf4j
@AllArgsConstructor
public class ReadingDiagnosticVoucherServiceImpl implements ReadingDiagnosticVoucherService {
    private final ReadingDiagnosticVoucherMapper readingDiagnosticVoucherMapper;
    private final ReadingDiagnosticVoucherRepository readingDiagnosticVoucherRepository;
    private final InstituteRepository instituteRepository;
    private final ReadingDiagnosticTicketService readingDiagnosticTicketService;

    /**
     * 독서능력진단평가 바우처 등록
     *
     * @param createReadingDiagnosticVoucherRequest 독서능력진단평가 바우처 등록 정보를 담은 DTO
     * @return 등록된 독서능력진단평가 바우처 응답 객체
     */
    @Override
    @Transactional
    public ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherResponse create(
            ReadingDiagnosticVoucherDto.CreateReadingDiagnosticVoucherRequest createReadingDiagnosticVoucherRequest
    ) {
        // 독서능력진단평가 바우처를 사용하는 교육기관 엔티티를 조회
        Institute institute = instituteRepository.findById(createReadingDiagnosticVoucherRequest.instituteId())
                .orElseThrow(() -> new EntityNotFoundException("교육기관을 찾을 수 없습니다. instituteId: " + createReadingDiagnosticVoucherRequest.instituteId()));
        // 바우처 코드(8자리의 영어 대문자, 숫자) 랜덤 생성(중복하는 경우 다시 생성)
        String characterPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        RandomGenerator randomGenerator = RandomGeneratorFactory.getDefault().create();
        Set<String> candidateVoucherCodes = new HashSet<>();
        List<String> existingVoucherCodes;
        do {
            // 2-1) 부족한 개수만큼 코드 생성 (여기선 1개)
            while (candidateVoucherCodes.isEmpty()) {
                StringBuilder codeStringBuilder = new StringBuilder(8);
                for (int index = 0; index < 8; index++) {
                    codeStringBuilder.append(
                            characterPool.charAt(randomGenerator.nextInt(characterPool.length()))
                    );
                }
                candidateVoucherCodes.add(codeStringBuilder.toString());
            }
            // 2-2) DB에 이미 존재하는 코드 한 번에 조회 (JPQL 메서드 필요)
            existingVoucherCodes = readingDiagnosticVoucherRepository.findAllCodesByCodeIn(candidateVoucherCodes);
            // 2-3) 중복된 코드는 후보에서 제거
            existingVoucherCodes.forEach(candidateVoucherCodes::remove);
        } while (candidateVoucherCodes.isEmpty());
        String voucherCode = candidateVoucherCodes.iterator().next();
        // 바우처 엔티티 생성 및 저장
        ReadingDiagnosticVoucher readingDiagnosticVoucher = readingDiagnosticVoucherMapper.toCreateEntity(
                createReadingDiagnosticVoucherRequest,
                voucherCode,
                institute
        );
        readingDiagnosticVoucher = readingDiagnosticVoucherRepository.save(readingDiagnosticVoucher);
        // 채점권 엔티티를 생성하여 저장(등록 요청 객체의 ticketQuantity 개수만큼 생성)
        readingDiagnosticTicketService.createReadingDiagnosticTicket(
                readingDiagnosticVoucher,
                createReadingDiagnosticVoucherRequest.ticketQuantity()
        );
        return readingDiagnosticVoucherMapper.toResponseDto(readingDiagnosticVoucher);
    }

    /**
     * 조건에 맞는 독서능력진단평가 바우처 목록 조회
     *
     * @param findReadingDiagnosticVoucherRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 독서능력진단평가 바우처 목록 및 페이징 정보
     */
    @Override
    public ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherPageResponse find(ReadingDiagnosticVoucherDto.FindReadingDiagnosticVoucherRequest findReadingDiagnosticVoucherRequest) {
        return readingDiagnosticVoucherMapper.toPageResponseDto(readingDiagnosticVoucherRepository.findAll(
                ReadingDiagnosticVoucherSpecification.findWith(readingDiagnosticVoucherMapper.toCriteria(findReadingDiagnosticVoucherRequest)),
                (findReadingDiagnosticVoucherRequest.page() != null && findReadingDiagnosticVoucherRequest.size() != null)
                        ? PageRequest.of(findReadingDiagnosticVoucherRequest.page(), findReadingDiagnosticVoucherRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 독서능력진단평가 바우처 조회
     *
     * @param readingDiagnosticVoucherId 독서능력진단평가 바우처 고유번호
     * @return 특정 독서능력진단평가 바우처 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherResponse findById(Long readingDiagnosticVoucherId) {
        return readingDiagnosticVoucherMapper.toResponseDto(getReadingDiagnosticVoucher(readingDiagnosticVoucherId));
    }

    /**
     * 특정 독서능력진단평가 바우처 수정
     *
     * @param readingDiagnosticVoucherId            수정할 독서능력진단평가 바우처의 ID
     * @param updateReadingDiagnosticVoucherRequest 독서능력진단평가 바우처 수정 요청 객체
     */
    @Override
    @Transactional
    public void update(
            Long readingDiagnosticVoucherId,
            ReadingDiagnosticVoucherDto.UpdateReadingDiagnosticVoucherRequest updateReadingDiagnosticVoucherRequest
    ) {
        // 수정할 독서능력진단평가 바우처 엔티티 조회
        ReadingDiagnosticVoucher readingDiagnosticVoucher = getReadingDiagnosticVoucher(readingDiagnosticVoucherId);
        // 수정 요청 객체에 교육기관 고유번호가 있으면 교육기관 엔티티 조회
        Institute institute = Optional.ofNullable(updateReadingDiagnosticVoucherRequest.instituteId())
                .map(instituteId -> instituteRepository.findById(instituteId)
                        .orElseThrow(() -> new EntityNotFoundException("교육기관이 존재하지 않습니다. instituteId: " + instituteId)))
                .orElseGet(readingDiagnosticVoucher::getInstitute);
        // 독서능력진단평가 바우처 엔티티 빌드 후 UPDATE
        readingDiagnosticVoucherRepository.save(readingDiagnosticVoucherMapper.toUpdateEntity(
                readingDiagnosticVoucher,
                updateReadingDiagnosticVoucherRequest,
                institute
        ));
    }

    /**
     * 특정 독서능력진단평가 바우처 삭제
     *
     * @param readingDiagnosticVoucherId 삭제할 독서능력진단평가 바우처의 ID
     */
    @Override
    @Transactional
    public void delete(Long readingDiagnosticVoucherId) {
        readingDiagnosticVoucherRepository.delete(getReadingDiagnosticVoucher(readingDiagnosticVoucherId));
    }

    /**
     * 특정 독서능력진단평가 바우처 엔티티 조회
     * @param readingDiagnosticVoucherId 독서능력진단평가 바우처 고유번호
     * @return 특정 독서능력진단평가 바우처 엔티티 객체
     */
    @Transactional(readOnly = true)
    public ReadingDiagnosticVoucher getReadingDiagnosticVoucher(Long readingDiagnosticVoucherId) {
        return readingDiagnosticVoucherRepository.findById(readingDiagnosticVoucherId)
                .orElseThrow(() -> new EntityNotFoundException("지사를 찾을 수 없습니다. readingDiagnosticVoucherId: " + readingDiagnosticVoucherId));
    }
}