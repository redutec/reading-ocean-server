package com.redutec.admin.branch.service;

import com.redutec.core.dto.BranchDto;
import com.redutec.core.mapper.BranchMapper;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.entity.Branch;
import com.redutec.core.entity.Teacher;
import com.redutec.core.repository.BranchRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.specification.BranchSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final TeacherRepository teacherRepository;
    private final FileUtil fileUtil;

    /**
     * 지사 등록
     * @param createBranchRequest 지사 등록 정보를 담은 DTO
     * @return 등록된 지사 정보
     */
    @Override
    @Transactional
    public BranchDto.BranchResponse create(BranchDto.CreateBranchRequest createBranchRequest) {
        // 등록 요청 객체에 교사 고유번호가 있으면 교사 엔티티 조회
        Teacher managerTeacher = Optional.ofNullable(createBranchRequest.managerTeacherId())
                .map(managerTeacherId -> teacherRepository.findById(managerTeacherId)
                        .orElseThrow(() -> new EntityNotFoundException("지사장 교사를 찾을 수 없습니다. managerTeacherId: " + managerTeacherId)))
                .orElse(null);
        // 계약서 파일이 존재하는 경우 파일을 업로드하고 파일명을 가져오기(파일이 없으면 파일명은 null)
        String contractFileName = Optional.ofNullable(createBranchRequest.contractFile())
                .filter(file -> !file.isEmpty())
                .map(file -> {
                    FileUploadResult result = fileUtil.uploadFile(file, "/branch");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        // 신규 지사 INSERT 후 등록한 지사 정보를 응답 객체에 담아 리턴
        return branchMapper.toResponseDto(branchMapper.toCreateEntity(
                createBranchRequest,
                managerTeacher,
                contractFileName
        ));
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
     * 특정 지사 수정
     * @param branchId 수정할 지사의 ID
     * @param updateBranchRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long branchId, BranchDto.UpdateBranchRequest updateBranchRequest) {
        // 수정할 지사 엔티티 조회
        Branch branch = getBranch(branchId);
        // 업로드할 계약서 파일이 있는 경우 업로드하고 파일명을 생성
        String contractFileName = Optional.ofNullable(updateBranchRequest.contractFile())
                .filter(contractFile -> !contractFile.isEmpty())
                .map(contractFile -> {
                    FileUploadResult result = fileUtil.uploadFile(contractFile, "/branch");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElseGet(branch::getContractFileName);
        // 수정 요청 객체에 교사 고유번호가 있으면 교사 엔티티 조회
        Teacher managerTeacher = Optional.ofNullable(updateBranchRequest.managerTeacherId())
                .map(managerTeacherId -> teacherRepository.findById(managerTeacherId)
                        .orElseThrow(() -> new EntityNotFoundException("지사장 교사를 찾을 수 없습니다. managerTeacherId: " + managerTeacherId)))
                .orElseGet(branch::getManagerTeacher);
        // 지사 수정 엔티티 빌드 후 UPDATE
        branchRepository.save(branchMapper.toUpdateEntity(
                branch,
                updateBranchRequest,
                managerTeacher,
                contractFileName
        ));
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

    /**
     * 특정 지사 엔티티 조회
     * @param branchId 지사 고유번호
     * @return 특정 지사 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Branch getBranch(Long branchId) {
        return branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("지사를 찾을 수 없습니다. branchId: " + branchId));
    }
}