package com.redutec.admin.learning.material.service;

import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.dto.LearningMaterialDto;
import com.redutec.core.entity.LearningMaterial;
import com.redutec.core.mapper.LearningMaterialMapper;
import com.redutec.core.repository.LearningMaterialRepository;
import com.redutec.core.specification.LearningMaterialSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class LearningMaterialServiceImpl implements LearningMaterialService {
    private final LearningMaterialMapper learningMaterialMapper;
    private final LearningMaterialRepository learningMaterialRepository;
    private final FileUtil fileUtil;

    /**
     * 학습 자료 등록
     * @param createLearningMaterialRequest 학습 자료 등록 정보를 담은 DTO
     * @return 등록된 학습 자료 정보
     */
    @Override
    @Transactional
    public LearningMaterialDto.LearningMaterialResponse create(
            LearningMaterialDto.CreateLearningMaterialRequest createLearningMaterialRequest
    ) {
        // 업로드된 파일이 있으면 각각 업로드하고, 파일명만 추출해 리스트에 담기
        List<String> attachmentFileNames = new ArrayList<>();
        List<MultipartFile> files = createLearningMaterialRequest.attachmentFiles();
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    FileUploadResult result = fileUtil.uploadFile(file, "/hq-document");
                    // 파일 경로 중 파일명만 추출
                    String filename = Paths.get(result.filePath())
                            .getFileName()
                            .toString();
                    attachmentFileNames.add(filename);
                }
            }
        }
        // 학습 자료 게시물 등록
        return learningMaterialMapper.toResponseDto(
                learningMaterialRepository.save(
                        learningMaterialMapper.createEntity(
                                createLearningMaterialRequest,
                                attachmentFileNames
                        )
                )
        );
    }

    /**
     * 조건에 맞는 학습 자료 목록 조회
     * @param findLearningMaterialRequest 조회 조건을 담은 DTO
     * @return 조회된 학습 자료 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public LearningMaterialDto.LearningMaterialPageResponse find(
            LearningMaterialDto.FindLearningMaterialRequest findLearningMaterialRequest
    ) {
        return learningMaterialMapper.toPageResponseDto(learningMaterialRepository.findAll(
                LearningMaterialSpecification.findWith(learningMaterialMapper.toCriteria(findLearningMaterialRequest)),
                (findLearningMaterialRequest.page() != null && findLearningMaterialRequest.size() != null)
                        ? PageRequest.of(findLearningMaterialRequest.page(), findLearningMaterialRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 학습 자료 조회
     * @param learningMaterialId 학습 자료 고유번호
     * @return 특정 학습 자료 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public LearningMaterialDto.LearningMaterialResponse findById(Long learningMaterialId) {
        return learningMaterialMapper.toResponseDto(getLearningMaterial(learningMaterialId));
    }

    /**
     * 특정 학습 자료 수정
     * @param learningMaterialId 수정할 학습 자료의 ID
     * @param updateLearningMaterialRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long learningMaterialId,
            LearningMaterialDto.UpdateLearningMaterialRequest updateLearningMaterialRequest
    ) {
        // 수정할 학습 자료 게시물 엔티티 조회
        LearningMaterial learningMaterial = getLearningMaterial(learningMaterialId);
        // 새로 업로드된 파일 리스트
        List<MultipartFile> files = updateLearningMaterialRequest.attachmentFiles();
        // 신규 파일이 하나라도 있으면 기존 파일 삭제 후 새 파일 업로드, 없으면 기존 유지
        List<String> attachmentFileNames = Optional.ofNullable(files)
                .filter(fs -> fs.stream().anyMatch(f -> !f.isEmpty()))
                .map(fs -> {
                    // 기존 파일 삭제
                    learningMaterial.getAttachmentFileNames()
                            .forEach(oldName -> fileUtil.deleteFile("/hq-document", oldName));
                    // 새 파일 업로드 후 파일명 리스트 생성
                    return fs.stream()
                            .filter(f -> !f.isEmpty())
                            .map(f -> {
                                FileUploadResult result = fileUtil.uploadFile(f, "/hq-document");
                                return Paths.get(result.filePath()).getFileName().toString();
                            })
                            .toList();
                })
                .orElseGet(learningMaterial::getAttachmentFileNames);
        // 학습 자료 게시물 수정
        learningMaterialMapper.updateEntity(
                learningMaterial,
                attachmentFileNames,
                updateLearningMaterialRequest
        );
    }

    /**
     * 특정 학습 자료 삭제
     * @param learningMaterialId 삭제할 학습 자료의 ID
     */
    @Override
    @Transactional
    public void delete(Long learningMaterialId) {
        learningMaterialRepository.delete(getLearningMaterial(learningMaterialId));
    }

    /**
     * 특정 학습 자료 엔티티 조회
     * @param learningMaterialId 학습 자료 고유번호
     * @return 특정 학습 자료 엔티티 객체
     */
    @Transactional(readOnly = true)
    public LearningMaterial getLearningMaterial(Long learningMaterialId) {
        return learningMaterialRepository.findById(learningMaterialId)
                .orElseThrow(() -> new EntityNotFoundException("학습 자료 게시물을 찾을 수 없습니다. learningMaterialId: " + learningMaterialId));
    }
}