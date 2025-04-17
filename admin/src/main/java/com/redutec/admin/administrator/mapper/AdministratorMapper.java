package com.redutec.admin.administrator.mapper;

import com.redutec.admin.administrator.dto.AdministratorDto;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.config.EncryptUtil;
import com.redutec.core.criteria.AdministratorCriteria;
import com.redutec.core.entity.Administrator;
import com.redutec.core.entity.AdministratorMenu;
import com.redutec.core.meta.AuthenticationStatus;
import com.redutec.core.repository.AdministratorMenuRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class AdministratorMapper {
    private final EncryptUtil encryptUtil;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AdministratorMenuRepository administratorMenuRepository;

    /**
     * CreateAdministratorRequest DTO를 기반으로 Administrator 엔티티를 생성합니다.
     *
     * @param createAdministratorRequest 시스템 관리자 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Administrator 엔티티
     */
    public Administrator toEntity(
            AdministratorDto.CreateAdministratorRequest createAdministratorRequest
    ) {
        return Administrator.builder()
                .email(createAdministratorRequest.email())
                .password(passwordEncoder.encode(createAdministratorRequest.password()))
                .nickname(createAdministratorRequest.nickname())
                .failedLoginAttempts(0)
                .role(createAdministratorRequest.role())
                .authenticationStatus(AuthenticationStatus.ACTIVE)
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindAdministratorRequest 객체를 기반으로
     * AdministratorCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 시스템 관리자 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 AdministratorCriteria 객체
     */
    public AdministratorCriteria toCriteria(
            AdministratorDto.FindAdministratorRequest findAdministratorRequest
    ) {
        return new AdministratorCriteria(
                findAdministratorRequest.administratorIds(),
                findAdministratorRequest.nickname(),
                findAdministratorRequest.email(),
                findAdministratorRequest.roles(),
                findAdministratorRequest.authenticationStatuses()
        );
    }

    /**
     * Administrator 엔티티를 기반으로 응답용 AdministratorResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param administrator 변환할 Administrator 엔티티 (null 가능)
     * @return Administrator 엔티티의 데이터를 담은 AdministratorResponse DTO, administrator가 null이면 null 반환
     */
    public AdministratorDto.AdministratorResponse toResponseDto(
            Administrator administrator
    ) {
        return Optional.ofNullable(administrator)
                .map(admin -> new AdministratorDto.AdministratorResponse(
                        admin.getId(),
                        admin.getEmail(),
                        admin.getNickname(),
                        administratorMenuRepository.findAllByAccessibleRolesContains(admin.getRole()).stream()
                                .map(AdministratorMenu::getId)
                                .collect(Collectors.toList()),
                        admin.getRole(),
                        admin.getCreatedAt(),
                        admin.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 Administrator 엔티티 목록을 AdministratorPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param administratorPage Page 형태로 조회된 Administrator 엔티티 목록 (null 가능)
     * @return Administrator 엔티티 리스트와 페이지 정보를 담은 AdministratorPageResponse DTO, administratorPage가 null이면 null 반환
     */
    public AdministratorDto.AdministratorPageResponse toPageResponseDto(Page<Administrator> administratorPage) {
        return Optional.ofNullable(administratorPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new AdministratorDto.AdministratorPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}