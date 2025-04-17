package com.redutec.admin.v1.backoffice.service;

import com.redutec.admin.v1.backoffice.dto.BackOfficeUserDto;
import com.redutec.admin.v1.backoffice.mapper.BackOfficeUserMapper;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.config.v1.EncryptionUtil;
import com.redutec.core.entity.v1.BotGroup;
import com.redutec.core.entity.v1.BotUser;
import com.redutec.core.entity.v1.BotUserGroup;
import com.redutec.core.entity.v1.key.BotUserGroupKey;
import com.redutec.core.repository.v1.BotUserRepository;
import com.redutec.core.specification.v1.BotUserSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackOfficeUserServiceImpl implements BackOfficeUserService {
    private final BackOfficeUserMapper backOfficeUserMapper;
    private final BotUserRepository botUserRepository;
    private final BackOfficeGroupService backOfficeGroupService;
    private final JwtUtil jwtUtil;

    /**
     * 관리자 계정 등록
     * @param createBackOfficeUserRequest 관리자 계정 등록 정보를 담은 DTO
     * @return 등록된 관리자 계정 정보
     */
    @Override
    @Transactional
    public BackOfficeUserDto.BackOfficeUserResponse create(
            BackOfficeUserDto.CreateBackOfficeUserRequest createBackOfficeUserRequest
    ) throws NoSuchAlgorithmException {
        // Mapper 클래스로 BotUser 엔티티 생성 후 저장 -> 이 시점에서 userNo가 할당됨
        BotUser botUser = botUserRepository.save(backOfficeUserMapper.toEntity(createBackOfficeUserRequest));
        // 요청에 groupNo가 있다면 BotUserGroup 엔티티 생성
        if (createBackOfficeUserRequest.groupNo() != null) {
            // BotGroup 조회
            BotGroup botGroup = backOfficeGroupService.getBackOfficeGroup(createBackOfficeUserRequest.groupNo());
            // 복합키 생성 : savedUser의 userNo와 botGroup의 groupNo 사용
            BotUserGroupKey key = new BotUserGroupKey(botUser.getUserNo(), botGroup.getGroupNo());
            // BotUserGroup 생성
            BotUserGroup botUserGroup = BotUserGroup.builder()
                    .id(key)
                    .user(botUser)
                    .group(botGroup)
                    .useYn("Y")
                    .adminId(botUser.getAdminId())
                    .build();
            // BotUser의 userGroups 컬렉션에 추가 (cascade 옵션에 의해 함께 persist됨)
            botUser.getUserGroups().add(botUserGroup);
            // 변경사항 반영 (선택 사항: cascade가 제대로 동작하면 별도 save 호출 없이 persist될 수 있음)
            botUser = botUserRepository.save(botUser);
        }
        return backOfficeUserMapper.toResponse(botUser);
    }

    /**
     * 조건에 맞는 관리자 계정 목록 조회
     * @param findBackOfficeUserRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 계정 목록
     */
    @Override
    @Transactional(readOnly = true)
    public BackOfficeUserDto.BackOfficeUserPageResponse find(
            BackOfficeUserDto.FindBackOfficeUserRequest findBackOfficeUserRequest
    ) {
        // 조건에 맞는 관리자 계정 조회
        Page<BotUser> botUserPage = botUserRepository.findAll(
                BotUserSpecification.findWith(backOfficeUserMapper.toCriteria(findBackOfficeUserRequest)),
                (findBackOfficeUserRequest.page() != null && findBackOfficeUserRequest.size() != null)
                        ? PageRequest.of(findBackOfficeUserRequest.page(), findBackOfficeUserRequest.size())
                        : Pageable.unpaged()
        );
        // 각 BotUser의 userGroups과 그 안의 BotGroup.userGroups를 강제로 초기화 (필요시)
        botUserPage.getContent().forEach(botUser ->
                botUser.getUserGroups().forEach(botUserGroup ->
                        Hibernate.initialize(botUserGroup.getGroup().getUserGroups())
                )
        );
        // Mapper 클래스를 이용하여 Page 객체를 응답 DTO로 변환 후 리턴
        return backOfficeUserMapper.toPageResponseDto(botUserPage);
    }

    @Override
    @Transactional(readOnly = true)
    public BotUser getBackOfficeUser(
            Integer userNo
    ) {
        return botUserRepository.findByUserNoWithGroups(userNo).orElseThrow(() -> new EntityNotFoundException("No such BotUser"));
    }

    /**
     * 관리자 계정 수정
     * @param userNo 수정할 관리자 계정의 고유번호
     * @param updateBackOfficeUserRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Integer userNo,
            BackOfficeUserDto.UpdateBackOfficeUserRequest updateBackOfficeUserRequest
    ) {
        // 수정할 관리자 계정 조회
        BotUser botUser = getBackOfficeUser(userNo);
        // 비밀번호 변경 처리: Optional을 사용하여 newPassword가 존재할 경우 암호화 및 솔트 생성
        Optional<String> newPasswordOptional = Optional.ofNullable(updateBackOfficeUserRequest.newPassword());
        String passwordSaltValue = newPasswordOptional.map(pwd -> EncryptionUtil.getSalt()).orElse(null);
        String newPassword = newPasswordOptional.map(pwd -> {
            try {
                return EncryptionUtil.password(pwd, passwordSaltValue);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }).orElse(null);
        // DTO에 전달된 userGroups 정보를 Optional로 처리하여 엔티티로 변환
        List<BotUserGroup> newBackOfficeUserGroupList = Optional.ofNullable(updateBackOfficeUserRequest.userGroups())
                .map(list -> list.stream()
                        .map(dtoGroup -> {
                            // BotGroup 정보에서 그룹 번호 추출 (없으면 예외 발생)
                            Integer groupNo = Optional.ofNullable(dtoGroup.getGroup())
                                    .map(BotGroup::getGroupNo)
                                    .orElseThrow(() -> new IllegalArgumentException("BackOfficeGroup 정보가 올바르지 않습니다."));
                            // BotGroup 엔티티 조회
                            BotGroup botGroup = backOfficeGroupService.getBackOfficeGroup(groupNo);
                            // BotUserGroup 엔티티 생성
                            return BotUserGroup.builder()
                                    .id(new BotUserGroupKey(botUser.getUserNo(), botGroup.getGroupNo()))
                                    .user(botUser)
                                    .group(botGroup)
                                    .useYn(Optional.ofNullable(dtoGroup.getUseYn()).orElse("Y"))
                                    .description(dtoGroup.getDescription())
                                    .adminId(jwtUtil.extractUsername(
                                            (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                                    .build();
                        })
                        .collect(Collectors.toList())
                )
                .orElse(null);
        // 엔티티 업데이트: 각 필드는 null이면 기존 값 유지
        botUser.updateBotUser(
                updateBackOfficeUserRequest.userId(),
                updateBackOfficeUserRequest.userName(),
                newPassword,
                passwordSaltValue,
                updateBackOfficeUserRequest.useYn(),
                updateBackOfficeUserRequest.lastAccessIp(),
                updateBackOfficeUserRequest.lastAccessDatetime(),
                jwtUtil.extractUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()),
                updateBackOfficeUserRequest.description(),
                newBackOfficeUserGroupList
        );
        // 변경된 엔티티 저장
        botUserRepository.save(botUser);
    }

    /**
     * 관리자 계정 삭제
     * @param userNo 삭제할 관리자 계정의 고유번호
     */
    @Override
    @Transactional
    public void delete(
            Integer userNo
    ) {
        botUserRepository.delete(getBackOfficeUser(userNo));
    }
}