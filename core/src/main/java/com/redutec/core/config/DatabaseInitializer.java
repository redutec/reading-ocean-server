package com.redutec.core.config;

import com.redutec.core.entity.Administrator;
import com.redutec.core.entity.AdminMenu;
import com.redutec.core.meta.SampleData;
import com.redutec.core.repository.AdminMenuRepository;
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
    private final AdminMenuRepository adminMenuRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void initializeDatabase() {
        // Admin 서비스의 샘플 데이터 생성
        createSampleAdministrator();
        createSampleParentAdminMenu();
        createSampleChildrenAdminMenu();
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
    protected void createSampleParentAdminMenu() {
        if (adminMenuRepository.count() == 0) {
            log.info("No parent admin menu data found. Creating sample parent admin menus.");
            var parentAdminMenus = Arrays.stream(SampleData.ParentAdminMenu.values())
                    .map(pam -> AdminMenu.builder()
                            .name(pam.getName())
                            .url(pam.getUrl())
                            .description(pam.getDescription())
                            .accessibleRoles(pam.getAccessibleRoles())
                            .depth(0)
                            .available(true)
                            .build())
                    .toList();
            adminMenuRepository.saveAll(parentAdminMenus);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createSampleChildrenAdminMenu() {
        if (!adminMenuRepository.existsByDepth(1)) {
            log.info("No children admin menu data found. Creating sample children admin menus.");
            // 먼저 모든 상위 메뉴를 Map으로 변환 (이름 -> Menu)
            Map<String, AdminMenu> parentMenuMap = adminMenuRepository.findAll().stream()
                    .collect(Collectors.toMap(AdminMenu::getName, Function.identity()));
            var childrenAdminMenus = Arrays.stream(SampleData.ChildrenAdminMenu.values())
                    .map(cam -> {
                        var parent = parentMenuMap.get(cam.getParentAdminMenu().getName());
                        if (parent == null) {
                            throw new EntityNotFoundException("Parent admin menu not found: " + cam.getParentAdminMenu().getName());
                        }
                        return AdminMenu.builder()
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
            adminMenuRepository.saveAll(childrenAdminMenus);
        }
    }
}