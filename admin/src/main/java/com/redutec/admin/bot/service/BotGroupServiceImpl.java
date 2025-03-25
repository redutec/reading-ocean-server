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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BotGroupServiceImpl implements BotGroupService {
    private final BotGroupRepository botGroupRepository;

    @Override
    public BotGroupDto.BotGroupResponse create(BotGroupDto.CreateBotGroup createBotGroupDto) {
        // 관리자 그룹 정보를 Insert 후 응답 객체에 담아 리턴
        return BotGroupDto.BotGroupResponse.fromEntity(botGroupRepository.save(BotGroup.builder()
                .groupName(createBotGroupDto.getGroupName())
                .description(createBotGroupDto.getDescription())
                .build()));
    }

    @Override
    public BotGroupDto.BotGroupPageResponse find(BotGroupDto.FindBotGroup findBotGroupDto) {
        // 조건에 맞는 관리자 그룹 조회
        Page<BotGroup> botGroupPage = botGroupRepository.findAll(
                BotGroupSpecification.findWith(findBotGroupDto.toCriteria()),
                (findBotGroupDto.getPage() != null && findBotGroupDto.getSize() != null)
                        ? PageRequest.of(findBotGroupDto.getPage(), findBotGroupDto.getSize())
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

    @Override
    public BotGroup findByGroupNo(Long groupNo) {
        return botGroupRepository.findById(groupNo).orElseThrow(() -> new EntityNotFoundException("No such BotGroup"));
    }

    @Override
    public void update(Long userNo, BotGroupDto.UpdateBotGroup updateBotGroupDto) {

    }

    @Override
    public void delete(Long userNo) {

    }
}