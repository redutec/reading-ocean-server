package com.redutec.admin.bot.service;

import com.redutec.admin.bot.dto.BotUserDto;
import com.redutec.core.entity.BotUser;
import com.redutec.core.repository.BotUserRepository;
import com.redutec.core.specification.BotUserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BotUserServiceImpl implements BotUserService {
    private final BotUserRepository botUserRepository;

    /**
     * 관리자 계정 등록
     * @param createBotUserDto 관리자 계정 등록 정보를 담은 DTO
     * @return 등록된 관리자 계정 정보
     */
    @Override
    public BotUserDto.BotUserResponse create(BotUserDto.CreateBotUser createBotUserDto) {
        return null;
    }

    /**
     * 조건에 맞는 관리자 계정 목록 조회
     * @param findBotUserDto 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 계정 목록
     */
    @Override
    public BotUserDto.BotUserPageResponse find(BotUserDto.FindBotUser findBotUserDto) {
        // 조건에 맞는 관리자 계정 조회
        Page<BotUser> botUserPage = botUserRepository.findAll(
                BotUserSpecification.findWith(findBotUserDto.toCriteria()),
                (findBotUserDto.getPage() != null && findBotUserDto.getSize() != null)
                        ? PageRequest.of(findBotUserDto.getPage(), findBotUserDto.getSize())
                        : Pageable.unpaged());
        // 조회한 관리자 계정을 fromEntity 메서드를 사용해 DTO로 변환
        List<BotUserDto.BotUserResponse> botUserList = botUserPage.getContent().stream()
                .map(BotUserDto.BotUserResponse::fromEntity)
                .collect(Collectors.toList());
        return BotUserDto.BotUserPageResponse.builder()
                .botUserList(botUserList)
                .totalElements(botUserPage.getTotalElements())
                .totalPages(botUserPage.getTotalPages())
                .build();
    }

    /**
     * 관리자 계정 수정
     * @param userNo 수정할 관리자 계정의 고유번호
     * @param updateBotUserDto 수정할 정보를 담은 DTO
     */
    @Override
    public void update(Long userNo, BotUserDto.UpdateBotUser updateBotUserDto) {

    }

    /**
     * 관리자 계정 삭제
     * @param userNo 삭제할 관리자 계정의 고유번호
     */
    @Override
    public void delete(Long userNo) {

    }
}
