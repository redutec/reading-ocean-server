package com.redutec.admin.bot.service;

import com.redutec.admin.bot.dto.BotGroupDto;
import com.redutec.core.entity.BotGroup;
import com.redutec.core.repository.BotGroupRepository;
import com.redutec.core.specification.BotGroupSpecification;
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
public class BotGroupServiceImpl implements BotGroupService {
    private final BotGroupRepository botGroupRepository;

    /**
     * 관리자 그룹 등록
     * @param createBotGroupRequest 관리자 그룹 등록 정보를 담은 DTO
     * @return 등록된 관리자 그룹 정보
     */
    @Override
    @Transactional
    public BotGroupDto.BotGroupResponse create(BotGroupDto.CreateBotGroupRequest createBotGroupRequest) {
        // 관리자 그룹 정보를 Insert 후 응답 객체에 담아 리턴
        return BotGroupDto.BotGroupResponse.fromEntity(botGroupRepository.save(BotGroup.builder()
                        .groupName(createBotGroupRequest.getGroupName())
                        .description(createBotGroupRequest.getDescription())
                        .useYn(createBotGroupRequest.getUseYn())
                        .build()));
    }

    /**
     * 조건에 맞는 관리자 그룹 목록 조회
     * @param findBotGroupRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 그룹 목록
     */
    @Override
    @Transactional(readOnly = true)
    public BotGroupDto.BotGroupPageResponse find(BotGroupDto.FindBotGroupRequest findBotGroupRequest) {
        // 조건에 맞는 관리자 그룹 조회
        Page<BotGroup> botGroupPage = botGroupRepository.findAll(
                BotGroupSpecification.findWith(findBotGroupRequest.toCriteria()),
                (findBotGroupRequest.getPage() != null && findBotGroupRequest.getSize() != null)
                        ? PageRequest.of(findBotGroupRequest.getPage(), findBotGroupRequest.getSize())
                        : Pageable.unpaged());
        // 조회한 관리자 그룹들을 fromEntity 메서드를 사용해 응답 객체로 변환 후 리턴
        List<BotGroupDto.BotGroupResponse> botGroupResponseList = botGroupPage.getContent().stream()
                .map(BotGroupDto.BotGroupResponse::fromEntity)
                .collect(Collectors.toList());
        return BotGroupDto.BotGroupPageResponse.builder()
                .botGroupList(botGroupResponseList)
                .totalElements(botGroupPage.getTotalElements())
                .totalPages(botGroupPage.getTotalPages())
                .build();
    }

    /**
     * 특정 관리자 그룹 조회
     * @param groupNo 관리자 그룹 고유번호
     * @return 특정 관리자 그룹 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BotGroup findByGroupNo(Long groupNo) {
        return botGroupRepository.findById(groupNo).orElseThrow(() -> new EntityNotFoundException("No such BotGroup"));
    }

    /**
     * 관리자 그룹 수정
     * @param groupNo 수정할 관리자 그룹의 고유번호
     * @param updateBotGroupRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long groupNo, BotGroupDto.UpdateBotGroupRequest updateBotGroupRequest) {
        // 수정할 관리자 그룹이 존재하는지 확인
        BotGroup botGroup = findByGroupNo(groupNo);
        // 도메인 메서드를 통해 필요한 필드만 업데이트 (비즈니스 로직을 캡슐화)
        botGroup.updateBotGroup(
                updateBotGroupRequest.getGroupName(),
                updateBotGroupRequest.getDescription(),
                updateBotGroupRequest.getUseYn());
        // 변경된 엔티티를 저장 (영속성 컨텍스트의 변경 감지를 통한 업데이트)
        botGroupRepository.save(botGroup);
    }

    /**
     * 관리자 그룹 삭제
     * @param groupNo 삭제할 관리자 그룹의 고유번호
     */
    @Override
    @Transactional
    public void delete(Long groupNo) {
        botGroupRepository.delete(findByGroupNo(groupNo));
    }
}