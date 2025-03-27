package com.redutec.admin.bot.service;

import com.redutec.admin.bot.dto.BotUserDto;
import com.redutec.admin.config.CurrentAdminUser;
import com.redutec.core.config.EncryptionUtil;
import com.redutec.core.entity.BotUser;
import com.redutec.core.entity.BotUserGroup;
import com.redutec.core.repository.BotUserRepository;
import com.redutec.core.specification.BotUserSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BotUserServiceImpl implements BotUserService {
    private final BotUserRepository botUserRepository;
    private final BotGroupService botGroupService;

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
        // 그룹 번호가 존재하면 해당 그룹을 조회하여 BotUserGroup을 생성, 없으면 빈 리스트 사용
        // Optional을 사용하여 if문 없이 처리
        Optional<BotUserGroup> optionalUserGroup = Optional.ofNullable(createBotUserRequest.getGroupNo())
                .map(groupNo -> {
                    var botGroup = botGroupService.findByGroupNo(groupNo);
                    return BotUserGroup.builder()
                            .group(botGroup)
                            .useYn("Y")
                            .adminId(CurrentAdminUser.getUserId() != null ? CurrentAdminUser.getUserId() : null)
                            .build();
                });
        // BotUser 엔티티 생성 시, userGroups를 빈 ArrayList로 초기화
        BotUser botUser = BotUser.builder()
                .userId(createBotUserRequest.getUserId())
                .userName(createBotUserRequest.getUserName())
                .password(encryptPassword)
                .passwordSaltValue(passwordSaltValue)
                .useYn("Y")
                .adminId(CurrentAdminUser.getUserId())
                .description(createBotUserRequest.getDescription())
                .userGroups(new ArrayList<>()) // mutable한 리스트 제공
                .build();
        // Optional에 값이 있으면 BotUserGroup의 user 필드를 현재 botUser로 지정한 후, BotUser의 userGroups에 추가
        optionalUserGroup.ifPresent(botUserGroup -> {
            // 빌더에서 user 필드를 설정할 수 있으므로 여기서 다시 빌더를 사용하지 않고,
            // 이미 생성된 botUserGroup 객체의 user 필드를 직접 설정하는 방법은 setter를 추가하지 않으므로,
            // botUserGroup 객체를 새로 빌드하면서 user를 할당합니다.
            BotUserGroup updatedUserGroup = BotUserGroup.builder()
                    .user(botUser)    // user 필드 설정
                    .group(botUserGroup.getGroup())
                    .useYn(botUserGroup.getUseYn())
                    .adminId(botUserGroup.getAdminId())
                    .build();
            botUser.getUserGroups().add(updatedUserGroup);
        });
        // BotUser 저장 (cascade 옵션에 의해 BotUserGroup도 함께 저장됨)
        BotUser savedUser = botUserRepository.save(botUser);
        return BotUserDto.BotUserResponse.fromEntity(savedUser);
    }

    /**
     * 조건에 맞는 관리자 계정 목록 조회
     * @param findBotUserRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 계정 목록
     */
    @Override
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
    public BotUser findByUserNo(Integer userNo) {
        return botUserRepository.findById(userNo).orElseThrow(() -> new EntityNotFoundException("No such BotUser"));
    }

    /**
     * 관리자 계정 수정
     * @param userNo 수정할 관리자 계정의 고유번호
     * @param updateBotUserRequest 수정할 정보를 담은 DTO
     */
    @Override
    public void update(Integer userNo, BotUserDto.UpdateBotUserRequest updateBotUserRequest) {

    }

    /**
     * 관리자 계정 삭제
     * @param userNo 삭제할 관리자 계정의 고유번호
     */
    @Override
    public void delete(Integer userNo) {
        botUserRepository.delete(findByUserNo(userNo));
    }
}
