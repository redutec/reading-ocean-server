package com.redutec.core.config;

import com.redutec.core.entity.Administrator;
import com.redutec.core.entity.AdministratorMenu;
import com.redutec.core.meta.SampleData;
import com.redutec.core.repository.AdministratorMenuRepository;
import com.redutec.core.repository.AdministratorRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
@Getter
@RequiredArgsConstructor
public class DatabaseInitializer {
    private final AdministratorRepository administratorRepository;
    private final AdministratorMenuRepository administratorMenuRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void initializeDatabase() {
        // Admin 서비스의 샘플 데이터 생성
        createSampleAdministrator();
        createSampleParentAdministratorMenu();
        createSampleChildrenAdministratorMenu();
    }

    // Admin 서비스용
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleAdministrator() {
        if (administratorRepository.count() == 0) {
            log.info("No administrator data found. Creating sample Administrators.");
            var administrators = Arrays.stream(SampleData.Administrator.values())
                    .map(administrator -> Administrator.builder()
                            .email(administrator.getEmail())
                            .password(passwordEncoder.encode(administrator.getPassword()))
                            .role(administrator.getRole())
                            .authenticationStatus(administrator.getAuthenticationStatus())
                            .nickname(administrator.getNickname())
                            .failedLoginAttempts(administrator.getFailedLoginAttempts())
                            .build())
                    .toList();
            administratorRepository.saveAll(administrators);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleParentAdministratorMenu() {
        if (administratorMenuRepository.count() == 0) {
            log.info("No parent administrator menu data found. Creating sample parent administrator menus.");
            var parentAdministratorMenus = Arrays.stream(SampleData.ParentAdministratorMenu.values())
                    .map(pam -> AdministratorMenu.builder()
                            .name(pam.getName())
                            .url(pam.getUrl())
                            .description(pam.getDescription())
                            .accessibleRoles(pam.getAccessibleRoles())
                            .depth(0)
                            .available(true)
                            .build())
                    .toList();
            administratorMenuRepository.saveAll(parentAdministratorMenus);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleChildrenAdministratorMenu() {
        if (!administratorMenuRepository.existsByDepth(1)) {
            log.info("No children administrator menu data found. Creating sample children administrator menus.");
            // 먼저 모든 상위 메뉴를 Map으로 변환 (이름 -> Menu)
            Map<String, AdministratorMenu> parentMenuMap = administratorMenuRepository.findAll().stream()
                    .collect(Collectors.toMap(AdministratorMenu::getName, Function.identity()));
            var childrenAdministratorMenus = Arrays.stream(SampleData.ChildrenAdministratorMenu.values())
                    .map(cam -> {
                        var parent = parentMenuMap.get(cam.getParentMenu().getName());
                        if (parent == null) {
                            throw new EntityNotFoundException("Parent administrator menu not found: " + cam.getParentMenu().getName());
                        }
                        return AdministratorMenu.builder()
                                .name(cam.getName())
                                .url(cam.getUrl())
                                .description(cam.getDescription())
                                .accessibleRoles(cam.getAccessibleRoles())
                                .depth(parent.getDepth() + 1)
                                .parent(parent)
                                .available(true)
                                .build();
                    })
                    .toList();
            administratorMenuRepository.saveAll(childrenAdministratorMenus);
        }
    }
}