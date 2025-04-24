package com.redutec.admin.institute.service;

import com.redutec.admin.institute.dto.InstituteDto;
import com.redutec.admin.institute.mapper.InstituteMapper;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.entity.Institute;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.specification.InstituteSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class InstituteServiceImpl implements InstituteService {
    private final InstituteMapper instituteMapper;
    private final InstituteRepository instituteRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUtil fileUtil;

    /**
     * 교육기관 등록
     * @param createInstituteRequest 교육기관 등록 정보를 담은 DTO
     * @return 등록된 교육기관 정보
     */
    @Override
    @Transactional
    public InstituteDto.InstituteResponse create(
            InstituteDto.CreateInstituteRequest createInstituteRequest
    ) {
        return instituteMapper.toResponseDto(
                instituteRepository.save(
                        instituteMapper.toEntity(
                                createInstituteRequest
                        )
                )
        );
    }

    /**
     * 조건에 맞는 교육기관 목록 조회
     * @param findInstituteRequest 조회 조건을 담은 DTO
     * @return 조회된 교육기관 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteDto.InstitutePageResponse find(
            InstituteDto.FindInstituteRequest findInstituteRequest
    ) {
        return instituteMapper.toPageResponseDto(instituteRepository.findAll(
                InstituteSpecification.findWith(instituteMapper.toCriteria(findInstituteRequest)),
                (findInstituteRequest.page() != null && findInstituteRequest.size() != null)
                        ? PageRequest.of(findInstituteRequest.page(), findInstituteRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 교육기관 조회
     * @param instituteId 교육기관 고유번호
     * @return 특정 교육기관 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteDto.InstituteResponse findById(
            Long instituteId
    ) {
        return instituteMapper.toResponseDto(getInstitute(instituteId));
    }

    /**
     * 특정 교육기관 엔티티 조회
     * @param instituteId 교육기관 고유번호
     * @return 특정 교육기관 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Institute getInstitute(
            Long instituteId
    ) {
        return instituteRepository.findById(instituteId)
                .orElseThrow(() -> new EntityNotFoundException("No such institute"));
    }

    /**
     * 특정 교육기관 수정
     * @param instituteId 수정할 교육기관의 ID
     * @param updateInstituteRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long instituteId,
            InstituteDto.UpdateInstituteRequest updateInstituteRequest
    ) {
        // 수정할 교육기관 엔티티 조회
        Institute institute = getInstitute(instituteId);
        // 현재 비밀번호와 기존 비밀번호가 일치하면 진행. 다르다면 예외처리
        Optional.of(updateInstituteRequest.currentPassword())
                .filter(pwd -> passwordEncoder.matches(pwd, institute.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다."));
        // 새로운 비밀번호를 암호화
        String encodedNewPassword = Optional.ofNullable(updateInstituteRequest.newPassword())
                .filter(pwd -> !pwd.isBlank())
                .map(passwordEncoder::encode)
                .orElse(null);
        // 업로드할 계약서 파일이 있는 경우 업로드하고 파일명을 생성
        String contractFileName = Optional.ofNullable(updateInstituteRequest.contractFileName())
                .filter(file -> !file.isEmpty())
                .map(file -> {
                    FileUploadResult result = fileUtil.uploadFile(file, "/institute");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        // UPDATE 도메인 메서드로 변환
        institute.updateInstitute(
                updateInstituteRequest.accountId(),
                encodedNewPassword,
                updateInstituteRequest.region(),
                updateInstituteRequest.name(),
                updateInstituteRequest.status(),
                updateInstituteRequest.businessArea(),
                updateInstituteRequest.managerName(),
                updateInstituteRequest.managerPhoneNumber(),
                updateInstituteRequest.managerEmail(),
                contractFileName,
                updateInstituteRequest.contractDate(),
                updateInstituteRequest.renewalDate(),
                updateInstituteRequest.description()
        );
        // 교육기관 엔티티 UPDATE
        instituteRepository.save(institute);
    }

    /**
     * 특정 교육기관 삭제
     * @param instituteId 삭제할 교육기관의 ID
     */
    @Override
    @Transactional
    public void delete(
            Long instituteId
    ) {
        instituteRepository.delete(getInstitute(instituteId));
    }
}