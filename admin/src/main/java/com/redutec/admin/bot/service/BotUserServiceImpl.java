package com.redutec.admin.bot.service;

import com.redutec.admin.bot.dto.BotUserDto;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.config.EncryptionUtil;
import com.redutec.core.entity.BotGroup;
import com.redutec.core.entity.BotUser;
import com.redutec.core.entity.BotUserGroup;
import com.redutec.core.entity.key.BotUserGroupKey;
import com.redutec.core.repository.BotUserRepository;
import com.redutec.core.specification.BotUserSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotUserServiceImpl implements BotUserService {
    private final BotUserRepository botUserRepository;
    private final BotGroupService botGroupService;
    private final JwtUtil jwtUtil;

    /**
     * 관리자 계정 등록
     * @param createBotUserRequest 관리자 계정 등록 정보를 담은 DTO
     * @return 등록된 관리자 계정 정보
     */
    @Override
    @Transactional
    public BotUserDto.BotUserResponse create(BotUserDto.CreateBotUserRequest createBotUserRequest) throws NoSuchAlgorithmException {
        // 비밀번호 암호화
        String passwordSaltValue = EncryptionUtil.getSalt();
        String encryptPassword = EncryptionUtil.password(createBotUserRequest.getPassword(), passwordSaltValue);
        // 현재 접속한 관리자 계정 아이디 조회
        String adminId = jwtUtil.extractUserIdFromToken((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // BotUser 엔티티 생성 (userGroups는 나중에 설정할 예정)
        BotUser botUser = BotUser.builder()
                .userId(createBotUserRequest.getUserId())
                .userName(createBotUserRequest.getUserName())
                .password(encryptPassword)
                .passwordSaltValue(passwordSaltValue)
                .useYn("Y")
                .adminId(adminId)
                .description(createBotUserRequest.getDescription())
                .build();
        // BotUser 저장 -> 이 시점에서 userNo가 할당됨
        BotUser savedUser = botUserRepository.save(botUser);
        // 요청에 groupNo가 있다면 BotUserGroup 엔티티 생성
        if (createBotUserRequest.getGroupNo() != null) {
            // BotGroup 조회
            BotGroup botGroup = botGroupService.findByGroupNo(createBotUserRequest.getGroupNo());
            // 복합키 생성 : savedUser의 userNo와 botGroup의 groupNo 사용
            BotUserGroupKey key = new BotUserGroupKey(savedUser.getUserNo(), botGroup.getGroupNo());
            // BotUserGroup 생성
            BotUserGroup botUserGroup = BotUserGroup.builder()
                    .id(key)
                    .user(savedUser)
                    .group(botGroup)
                    .useYn("Y")
                    .adminId(adminId != null ? adminId : "")
                    .build();
            // BotUser의 userGroups 컬렉션에 추가 (cascade 옵션에 의해 함께 persist됨)
            savedUser.getUserGroups().add(botUserGroup);
            // 변경사항 반영 (선택 사항: cascade가 제대로 동작하면 별도 save 호출 없이 persist될 수 있음)
            savedUser = botUserRepository.save(savedUser);
        }
        log.info("savedUser: {}", savedUser);
        return BotUserDto.BotUserResponse.fromEntity(savedUser);
    }

    /**
     * 조건에 맞는 관리자 계정 목록 조회
     * @param findBotUserRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 계정 목록
     */
    @Override
    @Transactional(readOnly = true)
    public BotUserDto.BotUserPageResponse find(BotUserDto.FindBotUserRequest findBotUserRequest) {
        // 조건에 맞는 관리자 계정 조회
        Page<BotUser> botUserPage = botUserRepository.findAll(
                BotUserSpecification.findWith(findBotUserRequest.toCriteria()),
                (findBotUserRequest.getPage() != null && findBotUserRequest.getSize() != null)
                        ? PageRequest.of(findBotUserRequest.getPage(), findBotUserRequest.getSize())
                        : Pageable.unpaged());
        // 조회한 관리자 계정들을 fromEntity 메서드를 사용해 응답 객체로 변환 후 리턴
        List<BotUserDto.BotUserResponse> botUserResponseList = botUserPage.getContent().stream()
                .map(BotUserDto.BotUserResponse::fromEntity)
                .collect(Collectors.toList());
        return BotUserDto.BotUserPageResponse.builder()
                .botUserList(botUserResponseList)
                .totalElements(botUserPage.getTotalElements())
                .totalPages(botUserPage.getTotalPages())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public BotUser findByUserNo(Integer userNo) {
        return botUserRepository.findByUserNoWithGroups(userNo)
                .orElseThrow(() -> new EntityNotFoundException("No such BotUser"));
    }

    /**
     * 관리자 계정 수정
     * @param userNo 수정할 관리자 계정의 고유번호
     * @param updateBotUserRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Integer userNo, BotUserDto.UpdateBotUserRequest updateBotUserRequest) {

    }

    /**
     * 관리자 계정 삭제
     * @param userNo 삭제할 관리자 계정의 고유번호
     */
    @Override
    @Transactional
    public void delete(Integer userNo) {
        botUserRepository.delete(findByUserNo(userNo));
    }
}
