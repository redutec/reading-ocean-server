package com.redutec.admin.hqdocument.service;

import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.dto.HeadquartersDocumentDto;
import com.redutec.core.entity.HeadquartersDocument;
import com.redutec.core.mapper.HeadquartersDocumentMapper;
import com.redutec.core.repository.HeadquartersDocumentRepository;
import com.redutec.core.specification.HeadquartersDocumentSpecification;
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
public class HeadquartersDocumentServiceImpl implements HeadquartersDocumentService {
    private final HeadquartersDocumentMapper headquartersDocumentMapper;
    private final HeadquartersDocumentRepository headquartersDocumentRepository;
    private final FileUtil fileUtil;

    /**
     * 본사 자료실 등록
     * @param createHeadquartersDocumentRequest 본사 자료실 등록 정보를 담은 DTO
     * @return 등록된 본사 자료실 정보
     */
    @Override
    @Transactional
    public HeadquartersDocumentDto.HeadquartersDocumentResponse create(
            HeadquartersDocumentDto.CreateHeadquartersDocumentRequest createHeadquartersDocumentRequest
    ) {
        // 업로드된 파일이 있으면 각각 업로드하고, 파일명만 추출해 리스트에 담기
        List<String> attachmentFileNames = new ArrayList<>();
        List<MultipartFile> files = createHeadquartersDocumentRequest.attachmentFiles();
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
        // 본사 자료실 게시물 등록
        return headquartersDocumentMapper.toResponseDto(
                headquartersDocumentRepository.save(
                        headquartersDocumentMapper.toCreateEntity(
                                createHeadquartersDocumentRequest,
                                attachmentFileNames
                        )
                )
        );
    }

    /**
     * 조건에 맞는 본사 자료실 목록 조회
     * @param findHeadquartersDocumentRequest 조회 조건을 담은 DTO
     * @return 조회된 본사 자료실 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public HeadquartersDocumentDto.HeadquartersDocumentPageResponse find(
            HeadquartersDocumentDto.FindHeadquartersDocumentRequest findHeadquartersDocumentRequest
    ) {
        return headquartersDocumentMapper.toPageResponseDto(headquartersDocumentRepository.findAll(
                HeadquartersDocumentSpecification.findWith(headquartersDocumentMapper.toCriteria(findHeadquartersDocumentRequest)),
                (findHeadquartersDocumentRequest.page() != null && findHeadquartersDocumentRequest.size() != null)
                        ? PageRequest.of(findHeadquartersDocumentRequest.page(), findHeadquartersDocumentRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 본사 자료실 조회
     * @param headquartersDocumentId 본사 자료실 고유번호
     * @return 특정 본사 자료실 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public HeadquartersDocumentDto.HeadquartersDocumentResponse findById(Long headquartersDocumentId) {
        return headquartersDocumentMapper.toResponseDto(getHeadquartersDocument(headquartersDocumentId));
    }

    /**
     * 특정 본사 자료실 수정
     * @param headquartersDocumentId 수정할 본사 자료실의 ID
     * @param updateHeadquartersDocumentRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long headquartersDocumentId,
            HeadquartersDocumentDto.UpdateHeadquartersDocumentRequest updateHeadquartersDocumentRequest
    ) {
        // 수정할 본사 자료실 게시물 엔티티 조회
        HeadquartersDocument headquartersDocument = getHeadquartersDocument(headquartersDocumentId);
        // 새로 업로드된 파일 리스트
        List<MultipartFile> files = updateHeadquartersDocumentRequest.attachmentFiles();
        // 신규 파일이 하나라도 있으면 기존 파일 삭제 후 새 파일 업로드, 없으면 기존 유지
        List<String> attachmentFileNames = Optional.ofNullable(files)
                .filter(fs -> fs.stream().anyMatch(f -> !f.isEmpty()))
                .map(fs -> {
                    // 기존 파일 삭제
                    headquartersDocument.getAttachmentFileNames()
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
                .orElseGet(headquartersDocument::getAttachmentFileNames);
        // 본사 자료실 게시물 수정
        headquartersDocumentRepository.save(
                headquartersDocumentMapper.toUpdateEntity(
                        headquartersDocument,
                        attachmentFileNames,
                        updateHeadquartersDocumentRequest
                )
        );
    }

    /**
     * 특정 본사 자료실 삭제
     * @param headquartersDocumentId 삭제할 본사 자료실의 ID
     */
    @Override
    @Transactional
    public void delete(Long headquartersDocumentId) {
        headquartersDocumentRepository.delete(getHeadquartersDocument(headquartersDocumentId));
    }

    /**
     * 특정 본사 자료실 엔티티 조회
     * @param headquartersDocumentId 본사 자료실 고유번호
     * @return 특정 본사 자료실 엔티티 객체
     */
    @Transactional(readOnly = true)
    public HeadquartersDocument getHeadquartersDocument(Long headquartersDocumentId) {
        return headquartersDocumentRepository.findById(headquartersDocumentId)
                .orElseThrow(() -> new EntityNotFoundException("본사 자료실 게시물을 찾을 수 없습니다. headquartersDocumentId: " + headquartersDocumentId));
    }
}