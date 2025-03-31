package com.redutec.admin.configuration.service;

import com.redutec.admin.configuration.dto.ConfigurationGeneralDto;
import com.redutec.core.entity.CmtConfigurationGeneral;
import com.redutec.core.repository.CmtConfigurationGeneralRepository;
import com.redutec.core.specification.CmtConfigurationGeneralSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfigurationGeneralServiceImpl implements ConfigurationGeneralService {
    private final CmtConfigurationGeneralRepository configurationGeneralRepository;
    private final CmtConfigurationGeneralRepository cmtConfigurationGeneralRepository;

    /**
     * 사이트 설정 등록
     * @param createConfigurationGeneralRequest 사이트 설정 등록 요청 객체
     * @return 등록한 사이트 설정 정보
     */
    @Override
    @Transactional
    public ConfigurationGeneralDto.ConfigurationGeneralResponse create(
            ConfigurationGeneralDto.CreateConfigurationGeneralRequest createConfigurationGeneralRequest
    ) {
        return ConfigurationGeneralDto.ConfigurationGeneralResponse.fromEntity(
                configurationGeneralRepository.save(CmtConfigurationGeneral.builder()
                        .configurationKey(createConfigurationGeneralRequest.getConfigurationKey())
                        .configurationCategoryKey(createConfigurationGeneralRequest.getConfigurationCategoryKey())
                        .configurationCategoryName(createConfigurationGeneralRequest.getConfigurationCategoryName())
                        .configurationName(createConfigurationGeneralRequest.getConfigurationName())
                        .configurationContent(createConfigurationGeneralRequest.getConfigurationContent())
                        .useYn(createConfigurationGeneralRequest.getUseYn())
                        .description(createConfigurationGeneralRequest.getDescription())
                        .build()));
    }

    /**
     * 조건에 맞는 사이트 설정 목록 조회
     */
    @Override
    @Transactional(readOnly = true)
    public ConfigurationGeneralDto.ConfigurationGeneralPageResponse find(
            ConfigurationGeneralDto.FindConfigurationGeneralRequest findConfigurationGeneralRequest
    ) {
        // 조건에 맞는 사이트 설정 페이징 목록 조회
        Page<CmtConfigurationGeneral> configurationGeneralPage = configurationGeneralRepository.findAll(
                CmtConfigurationGeneralSpecification.findWith(findConfigurationGeneralRequest.toCriteria()),
                (findConfigurationGeneralRequest.getPage() != null && findConfigurationGeneralRequest.getSize() != null)
                        ? PageRequest.of(findConfigurationGeneralRequest.getPage(), findConfigurationGeneralRequest.getSize())
                        : Pageable.unpaged()
        );
        // 조회된 엔티티들을 응답 DTO로 변환 후 리턴
        List<ConfigurationGeneralDto.ConfigurationGeneralResponse> configurationGeneralList = configurationGeneralPage.getContent().stream()
                .map(ConfigurationGeneralDto.ConfigurationGeneralResponse::fromEntity)
                .collect(Collectors.toList());
        return ConfigurationGeneralDto.ConfigurationGeneralPageResponse.builder()
                .configurationGeneralList(configurationGeneralList)
                .totalElements(configurationGeneralPage.getTotalElements())
                .totalPages(configurationGeneralPage.getTotalPages())
                .build();
    }

    /**
     * 특정 사이트 설정 상세 조회
     */
    @Override
    @Transactional(readOnly = true)
    public ConfigurationGeneralDto.ConfigurationGeneralResponse findByConfigurationKey(String configurationKey) {
        return ConfigurationGeneralDto.ConfigurationGeneralResponse.fromEntity(getCmtConfigurationGeneral(configurationKey));
    }

    @Override
    public CmtConfigurationGeneral getCmtConfigurationGeneral(String configurationKey) {
        return cmtConfigurationGeneralRepository.findById(configurationKey)
                .orElseThrow(() -> new EntityNotFoundException("No such configuration general with key: " + configurationKey));
    }

    /**
     * 사이트 설정 수정
     */
    @Override
    @Transactional
    public void update(
            String configurationKey,
            ConfigurationGeneralDto.UpdateConfigurationGeneralRequest updateConfigurationGeneralRequest
    ) {
        // 수정하려는 사이트 설정이 존재하는지 조회
        CmtConfigurationGeneral configurationGeneral = getCmtConfigurationGeneral(configurationKey);
        // 수정할 데이터를 엔티티에 설정 후 저장
        configurationGeneral.updateCmtConfigurationGeneral(
                updateConfigurationGeneralRequest.getConfigurationKey(),
                updateConfigurationGeneralRequest.getConfigurationCategoryKey(),
                updateConfigurationGeneralRequest.getConfigurationCategoryName(),
                updateConfigurationGeneralRequest.getConfigurationName(),
                updateConfigurationGeneralRequest.getConfigurationContent(),
                updateConfigurationGeneralRequest.getUseYn(),
                updateConfigurationGeneralRequest.getDescription()
        );
        configurationGeneralRepository.save(configurationGeneral);
    }

    /**
     * 사이트 설정 삭제
     */
    @Override
    @Transactional
    public void delete(String configurationKey) {
        configurationGeneralRepository.delete(getCmtConfigurationGeneral(configurationKey));
    }
}