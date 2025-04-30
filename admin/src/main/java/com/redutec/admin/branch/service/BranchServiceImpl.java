package com.redutec.admin.branch.service;

import com.redutec.admin.branch.dto.BranchDto;
import com.redutec.admin.branch.mapper.BranchMapper;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.entity.Branch;
import com.redutec.core.repository.BranchRepository;
import com.redutec.core.specification.BranchSpecification;
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
public class BranchServiceImpl implements BranchService {
    private final BranchMapper branchMapper;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUtil fileUtil;

    /**
     * 지사 등록
     * @param createBranchRequest 지사 등록 정보를 담은 DTO
     * @return 등록된 지사 정보
     */
    @Override
    @Transactional
    public BranchDto.BranchResponse create(BranchDto.CreateBranchRequest createBranchRequest) {
        return branchMapper.toResponseDto(branchRepository.save(branchMapper.toEntity(createBranchRequest)));
    }

    /**
     * 조건에 맞는 지사 목록 조회
     * @param findBranchRequest 조회 조건을 담은 DTO
     * @return 조회된 지사 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public BranchDto.BranchPageResponse find(BranchDto.FindBranchRequest findBranchRequest) {
        return branchMapper.toPageResponseDto(branchRepository.findAll(
                BranchSpecification.findWith(branchMapper.toCriteria(findBranchRequest)),
                (findBranchRequest.page() != null && findBranchRequest.size() != null)
                        ? PageRequest.of(findBranchRequest.page(), findBranchRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 지사 조회
     * @param branchId 지사 고유번호
     * @return 특정 지사 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BranchDto.BranchResponse findById(Long branchId) {
        return branchMapper.toResponseDto(getBranch(branchId));
    }

    /**
     * 특정 지사 엔티티 조회
     * @param branchId 지사 고유번호
     * @return 특정 지사 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Branch getBranch(Long branchId) {
        return branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("지사를 찾을 수 없습니다. id = " + branchId));
    }

    /**
     * 특정 지사 수정
     * @param branchId 수정할 지사의 ID
     * @param updateBranchRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long branchId, BranchDto.UpdateBranchRequest updateBranchRequest) {
        // 수정할 지사 엔티티 조회
        Branch branch = getBranch(branchId);
        // 현재 비밀번호와 기존 비밀번호가 일치하면 진행. 다르다면 예외처리
        Optional.of(updateBranchRequest.currentPassword())
                .filter(pwd -> passwordEncoder.matches(pwd, branch.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다"));
        // 새로운 비밀번호를 암호화
        String encodedNewPassword = Optional.ofNullable(updateBranchRequest.newPassword())
                .filter(pwd -> !pwd.isBlank())
                .map(passwordEncoder::encode)
                .orElse(null);
        // 업로드할 계약서 파일이 있는 경우 업로드하고 파일명을 생성
        String contractFileName = Optional.ofNullable(updateBranchRequest.contractFile())
                .filter(contractFile -> !contractFile.isEmpty())
                .map(contractFile -> {
                    FileUploadResult result = fileUtil.uploadFile(contractFile, "/branch");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        // UPDATE 도메인 메서드로 변환
        branch.updateBranch(
                updateBranchRequest.accountId(),
                encodedNewPassword,
                updateBranchRequest.region(),
                updateBranchRequest.name(),
                updateBranchRequest.status(),
                updateBranchRequest.businessArea(),
                updateBranchRequest.managerName(),
                updateBranchRequest.managerPhoneNumber(),
                updateBranchRequest.managerEmail(),
                contractFileName,
                updateBranchRequest.contractDate(),
                updateBranchRequest.renewalDate(),
                updateBranchRequest.description()
        );
        // 지사 엔티티 UPDATE
        branchRepository.save(branch);
    }

    /**
     * 특정 지사 삭제
     * @param branchId 삭제할 지사의 ID
     */
    @Override
    @Transactional
    public void delete(Long branchId) {
        branchRepository.delete(getBranch(branchId));
    }
}