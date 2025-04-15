package com.redutec.admin.backoffice.mapper;

import com.redutec.admin.backoffice.dto.BackOfficeGroupDto;
import com.redutec.admin.backoffice.dto.BackOfficeUserDto;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.config.EncryptionUtil;
import com.redutec.core.criteria.BotUserCriteria;
import com.redutec.core.entity.v1.BotUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class BackOfficeUserMapper {
    private final BackOfficeGroupMapper backOfficeGroupMapper;
    private final JwtUtil jwtUtil;

    /**
     * CreateBackOfficeUserRequest DTO를 기반으로 BotUser 엔티티를 생성합니다.
     * @param createBackOfficeUserRequest 사이트 설정 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 BotUser 엔티티
     */
    public BotUser toEntity(
            BackOfficeUserDto.CreateBackOfficeUserRequest createBackOfficeUserRequest
    ) throws NoSuchAlgorithmException {
        // 현재 접속 중인 관리자 아이디 조회(Null이면 "admin"으로 설정)
        String adminId = Optional.ofNullable(jwtUtil.extractUserIdFromToken((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .orElse("admin");
        // 비밀번호 솔트값 지정 및 비밀번호 암호화
        String passwordSaltValue = EncryptionUtil.getSalt();
        String password = EncryptionUtil.password(createBackOfficeUserRequest.password(), passwordSaltValue);
        // BotUser 엔티티 생성
        return BotUser.builder()
                .userId(createBackOfficeUserRequest.userId())
                .userName(createBackOfficeUserRequest.userName())
                .password(password)
                .passwordSaltValue(passwordSaltValue)
                .useYn("Y")
                .adminId(adminId)
                .description(createBackOfficeUserRequest.description())
                .build();
    }

    /**
     * FindBackOfficeUserRequest DTO를 기반으로 검색 조건 객체인 BotUserCriteria를 생성합니다.
     * 내부 검색 로직에서 사이트 설정 검색 조건을 구성할 때 사용됩니다.
     * @param findBackOfficeUserRequest 사이트 설정 조회 조건을 담은 DTO
     * @return 해당 요청의 필드를 이용해 생성된 BotUserCriteria 객체
     */
    public BotUserCriteria toCriteria(
            BackOfficeUserDto.FindBackOfficeUserRequest findBackOfficeUserRequest
    ) {
        return BotUserCriteria.builder()
                .userNoList(findBackOfficeUserRequest.userNoList())
                .userId(findBackOfficeUserRequest.userId())
                .userName(findBackOfficeUserRequest.userName())
                .build();
    }

    /**
     * BotUser 엔티티를 기반으로 응답용 BackOfficeUserResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     * @param botUser 변환할 엔티티 (null 가능)
     * @return BotUser 엔티티의 데이터를 담은 BackOfficeUserResponse DTO, company가 null이면 null 반환
     */
    public BackOfficeUserDto.BackOfficeUserResponse toResponse(
            BotUser botUser
    ) {
        BackOfficeGroupDto.BackOfficeGroupResponse botGroupResponse = Optional.ofNullable(botUser.getUserGroups())
                .filter(list -> !list.isEmpty())
                .map(list -> backOfficeGroupMapper.toResponse(list.getFirst().getGroup()))
                .orElse(null);
        return new BackOfficeUserDto.BackOfficeUserResponse(
                botUser.getUserNo(),
                botUser.getUserId(),
                botUser.getUserName(),
                botGroupResponse,
                botUser.getRegisterDatetime(),
                botUser.getModifyDatetime()
        );
    }

    /**
     * Page 형식의 엔티티 목록을 BackOfficeUserPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param botUserPage Page 형태로 조회된 엔티티 목록 (null 가능)
     * @return BotUser 엔티티 리스트와 페이지 정보를 담은 BackOfficeUserPageResponse DTO, botUserPage가 null이면 null 반환
     */
    public BackOfficeUserDto.BackOfficeUserPageResponse toPageResponseDto(
            Page<BotUser> botUserPage
    ) {
        return Optional.ofNullable(botUserPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponse)
                            .collect(Collectors.toList());
                    return new BackOfficeUserDto.BackOfficeUserPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}